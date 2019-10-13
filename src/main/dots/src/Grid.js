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

/* A simple grid with a variable size */
/* Most of the assignment involves changes to this class */

import React, {Component} from 'react';
import Button from './Button'

class Grid extends Component {
  constructor(props) {
    super(props);
    this.canvasReference = React.createRef();
    this.handleDrawClick = this.handleDrawClick.bind(this);
   // this.handleBackgroundImage =this.handleBackgroundImage.bind(this);
  }

  componentDidMount() {
    this.redraw();
  }

  componentDidUpdate() {
    this.redraw()
  }

  redraw = () => {
    let ctx = this.canvasReference.current.getContext('2d');
    ctx.clearRect(0, 0, this.props.width, this.props.height);
    var background = new Image();
    background.onload = () => {
      ctx.drawImage(background,0,0);
      let coordinates = this.getCoordinates();
      coordinates.forEach(coordinate => {
        this.drawCircle(ctx, coordinate);
      });
    }
    background.src = "image.jpg";
  };

  getCoordinates = () => {
      let coordinatesMatrix = [];
      var space = this.props.width/(this.props.size*1.0+1);
      for(var i= space ; i<this.props.width;i+= space){
         for(var j= space; j<this.props.height;j+= space){
             let pairXY = [i,j];
             coordinatesMatrix.push(pairXY);
         }
      }
    return coordinatesMatrix;

  };



  drawCircle = (ctx, coordinate) => {
    ctx.beginPath();
    ctx.arc(coordinate[0], coordinate[1], 20 / this.props.size, 0, 2 * Math.PI);
    ctx.fill();
  };

  handleDrawClick(){
    let ctx1 = this.canvasReference.current.getContext('2d');
    let edges = this.props.edges;
    let edgeArray = [];
    edgeArray= edges.split("\n");
    for(var i =0; i<edgeArray.length;i++){
       let startEndColor = [];
       startEndColor = edgeArray[i].split(" ");
       edgeArray[i] = startEndColor;
    }
    for(var j=0; j<edgeArray.length;j++){
       if (edgeArray[j].length < 2) continue;
       let startXY = edgeArray[j][0].split(",");
       edgeArray[j][0]=startXY;
       let endXY = edgeArray[j][1].split(",");
       edgeArray[j][1]=endXY;
    }
    var space = this.props.width/(this.props.size*1.0+1);
    console.log(edgeArray);
    for(var k=0; k<edgeArray.length;k++){
       if (edgeArray[k].length<2) continue;

       const startX = (parseInt(edgeArray[k][0][0])+1)*1.0*space;
       const startY = (parseInt(edgeArray[k][0][1])+1)*1.0*space;
       const endX = (parseInt(edgeArray[k][1][0])+1)*1.0*space;
       const endY = (parseInt(edgeArray[k][1][1])+1)*space;
       ctx1.beginPath();
       ctx1.lineWidth = "2";
       ctx1.strokeStyle = edgeArray[k][2];
       ctx1.moveTo(startX,startY);
       ctx1.lineTo(endX,endY);
       ctx1.stroke();
    }

    console.log('onClick');
  }

  handleBackgroundImage(event){
         console.log(event.target);
//         var filepath =  event.target.value.replace("C:\\fakepath\\", "");
//         console.log(filepath);
//         this.setState({image: filepath});

  }


  render() {
    return (
      <div id="canvas-div">
        <canvas ref={this.canvasReference} width={this.props.width} height={this.props.height} />
        <div className="center-text">Current Grid Size: {this.props.size}</div>
        <Button color="primary" onClick={this.handleDrawClick} value="Draw" />
        <Button color="secondary" onClick={this.redraw} value="Clear" />
        <input type ="file" id="file-id" onChange ={this.handleBackgroundImage} />


      </div>
    );
  }
}

export default Grid;
