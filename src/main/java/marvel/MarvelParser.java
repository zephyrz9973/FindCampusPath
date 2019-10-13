/*
 * Copyright ©2019 Hal Perkins.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Spring Quarter 2019 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package marvel;


import java.io.IOException;
import java.util.*;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import graph.Graph;

/** Parser utility to load the Marvel Comics dataset. */
public class MarvelParser {
//  public static void main(String[] args) {
//    //test parser:
////    MarvelParser newParser = new MarvelParser();
////    Map<String,ArrayList<String>> BookVSCharacter = MarvelParser.parseData("/Users/xuanhezhou/Downloads/cse331-19sp-xuanhz/src/main/resources/marvel/data/staffSuperheroes1.tsv");
////    for(String book: BookVSCharacter.keySet()){
////      System.out.println("book:"+book);
////      for(String heros: BookVSCharacter.get(book)){
////          System.out.println("   hero:"+heros);
////      }
////    }
////    System.out.println("test");
//    long startTime= System.currentTimeMillis();
//    MarvelParser newParser = new MarvelParser();
//    Map<String,ArrayList<String>> result = MarvelParser.parseData("/Users/xuanhezhou/Downloads/cse331-19sp-xuanhz/src/main/resources/marvel/data/marvelTest.tsv");
////    for(String i: result.keySet()){
////      System.out.println(i);
////      for(String j:result.get(i)){
////        System.out.println(j);
////      }
////    }
////    System.out.println(result.isEmpty());
////    for(String parentNode: newGraph.getParentNodeSet()){
////      System.out.println("parentNode:"+parentNode);
////      for(Graph.Edge edge: newGraph.getEdgeSet(parentNode)){
////        System.out.println("  Edge start:"+edge.getStartNode());
////        System.out.println("  Edge label:"+edge.getLabel());
////        System.out.println("  Edge end:"+edge.getEndNode());
////      }
////    }
//    long endTime = System.currentTimeMillis();
//    System.out.println("runtime:"+(endTime-startTime)+"ms");
//
//
//  }

  /**
   * Reads the Marvel Universe dataset. Each line of the input file contains a character name and a
   * comic book the character appeared in, separated by a tab character
   *
   * @spec.requires filename is a valid file path
   * @param filename the file that will be read
   * @return a map
   */

  // TODO: Pick your return type and document it
  public static Map<String,ArrayList<String>> parseData(String filename) {
    try {
      Reader reader = Files.newBufferedReader(Paths.get(filename));
      CsvToBean<UserModel> csvToBean = new CsvToBeanBuilder<UserModel>(reader)
              .withType(UserModel.class)
              .withIgnoreLeadingWhiteSpace(true)
              .withSeparator('\t')
              .build();

      Iterator<UserModel> csvUserIterator = csvToBean.iterator();
      Map<String, ArrayList<String>> bookVScharacter = new HashMap<>();
      while (csvUserIterator.hasNext()) {
        UserModel csvUser = csvUserIterator.next();
//        System.out.println(csvUser.getHero());
        if(!bookVScharacter.containsKey(csvUser.getBook())){
          bookVScharacter.put(csvUser.getBook(),new ArrayList<>());
        }
        ArrayList<String> newList = new ArrayList<String>(bookVScharacter.get(csvUser.getBook()));
        newList.add(csvUser.getHero());
        bookVScharacter.put(csvUser.getBook(),newList);
      }
      return bookVScharacter;
    }
    catch(FileNotFoundException e) {
      e.printStackTrace();
      System.out.println(filename + ": file not found");
      System.exit(1);
      return null; // is it okay?
    }
    catch(IOException e) {
      e.printStackTrace();
      System.exit(1);
      return null;
    }
  }

  public Graph<String,String> createGraph(String filePath){
  Map<String, ArrayList<String>> result = MarvelParser.parseData(filePath);
    Graph<String,String> newGraph = new Graph<>();
    for(String book : result.keySet()) {
      ArrayList<String> heroList = result.get(book);
      for (int i = 0; i < heroList.size(); i++) {
        String currentHero = heroList.get(i);
        if (!newGraph.containsNode(currentHero)) {
          newGraph.addNode(currentHero);
        }
        for (int j = i + 1; j < heroList.size(); j++) {
          newGraph.addEdge(currentHero, heroList.get(j), book);
          newGraph.addEdge(heroList.get(j),currentHero,book);
        }
      }
    }
     return newGraph;
  }


  public static class UserModel{
    @CsvBindByName(column = "hero")
    private String hero;

    @CsvBindByName(column = "book")
    private String book;

    public String getHero(){ return this.hero; }
    public void setHero(String newHero){
      this.hero = newHero;
    }
    public String getBook(){
      return this.book;
    }
    public void setBook(String newBook){
      this.book = newBook;
    }
  }
}

