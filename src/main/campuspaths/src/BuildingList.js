import React, {Component} from 'react';
import {Button} from "@material-ui/core";

class BuildingList extends Component {
  constructor(props) {
      super(props);
      // You don't need to store the string result of a request in
      // state, but we're using for this example so we can display
      // the string on the page.
       this.state = {
              buildingList: null
          };
       this.getBuildingList = this.getBuildingList.bind(this);
      }

  getBuildingList=()=>{
     fetch("http://localhost:4567/BuildingList")
             .then((res) => {
                 if (res.status !== 200) {
                   throw Error("The server could not process the request.");
                 }
                 return res.json();  // Hint: res.json() might be useful here. (Read the docs for it!)
               }
             ).then((resJson) => {
                console.log(resJson);
                console.log(resJson.length);
               // resText is the value from inside of res.text(), just like before.
               let string1 = "ShortName  LongName\n"
                 for(var i =0; i<resJson.length; i++){
                    string1 +=resJson[i][0];
                    string1 +="  "
                    string1 +=resJson[i][1];
                    string1 +="\n"
                 }
                 this.setState({
                   buildingList: string1
                 })
             }
           ).catch((error) => {
             // You can also call .catch() on the return value of the .then(), instead of giving
             // a second function as a parameter. It means the same thing as above, but it's a little
             // easier to read/understand what's going on.
             alert("The building you typed in do not exist. \n Click Get BuildingList to get shortName of building");
           });
  }


  render() {
    return (
      <div className="BuildingList">
          <p>
          {this.state.buildingList}
          </p>
          <Button variant ="contained" onClick={this.getBuildingList}> Get Building List</Button>
      </div>
    );
  }
}

export default BuildingList;