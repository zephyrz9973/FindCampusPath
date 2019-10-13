import React, {Component} from 'react';
import * as fetch from "node-fetch";
import Map from "./Map"
import BuildingPicker from "./BuildingPicker"
import {Button} from "@material-ui/core";
import BuildingList from "./BuildingList"

class MainContainer extends Component {

  constructor(props) {
    super(props);
    // You don't need to store the string result of a request in
    // state, but we're using for this example so we can display
    // the string on the page.
    this.state = {
        errorStatus: 0,
        start: null,
        end: null,
        requestResult: null
    };

    this.makeRequest = this.makeRequest.bind(this);
    this.handleStartBuilding =this.handleStartBuilding.bind(this);
    this.handleEndBuilding = this.handleEndBuilding.bind(this);
    this.handleReset = this.handleReset.bind(this);
//    this.componentDidUpdate = this.componentDidUpdate.bind(this);
  }

  makeRequest = () => {
      // This does the exact same thing as makeRequestLong(), in the exact same way.
      // It's just written using a much shorter syntax with less unnecessary variables.
      // The following is basically exactly the structure of what you're going to what to
      // use in HW9 to make a request: you can model your code off of this.
      // Make sure you understand how it works, so you can modify it to do what you want!
      fetch("http://localhost:4567/findPath?startShort="+this.state.start+"&endShort="+this.state.end)
        .then((res) => {
            if (res.status !== 200) {
              throw Error("The server could not process the request.");
            }
            return res.json();  // Hint: res.json() might be useful here. (Read the docs for it!)
          }
        ).then((resJson) => {
          // resText is the value from inside of res.text(), just like before.
          this.setState({
            requestResult: resJson
          });
        }
      ).catch((error) => {
        // You can also call .catch() on the return value of the .then(), instead of giving
        // a second function as a parameter. It means the same thing as above, but it's a little
        // easier to read/understand what's going on.
        alert("The building you typed in do not exist.Pick one from the hint!");
      });
    };

  handleStartBuilding(event){
      console.log("start:");
      console.log(this.state.start);
      this.setState({start: event.target.value})

      console.log(this.state.start);
   }

  handleEndBuilding(event){
      this.setState({end: event.target.value})
      console.log(this.state.end);
   }


  handleReset(event){
      this.setState({start: ""})
      this.setState({end: ""})
      this.setState({requestResult:""})
      console.log("START:"+this.state.start);
      console.log("END:"+this.state.end);

  }


  render() {
       return (
            <div className="FetchShortestPathData">
                <Map data={this.state.requestResult}/>
                <BuildingList/>
                <br/>
                <BuildingPicker startORend = "Start" onChange = {this.handleStartBuilding} value={this.state.start}/>
                <br/>
                <BuildingPicker startORend = "End" onChange = {this.handleEndBuilding} value={this.state.end}/>
                <br/>
                <Button variant="contained" onClick={this.makeRequest} color="primary">Find Shortest Path</Button>
                <br/>
                <Button variant="contained" onClick={this.handleReset} color="secondary">Reset</Button>
            </div>
        );
      }
}
  export default MainContainer;