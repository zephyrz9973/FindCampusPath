import React, {Component} from 'react';
import "./Map.css";

class Map extends Component {

  // NOTE:
  // This component is a suggestion for you to use, if you would like to.
  // It has some skeleton code that helps set up some of the more difficult parts
  // of getting <canvas> elements to display nicely.
  //
  // If you don't want to use this component, you're free to delete it.

  constructor(props) {
    super(props);
    this.canvasReference = React.createRef();
    this.backgroundImage = new Image();
    this.backgroundImage.onload = () => {
      this.drawBackgroundImage();
    };
    this.backgroundImage.src = "campus_map.jpg";

  }

  componentDidMount() {
    this.drawBackgroundImage();
    this.drawThePath();
  }

  componentDidUpdate(){
    this.drawBackgroundImage();
    this.drawThePath();
  }

  drawBackgroundImage = () => {
    let canvas = this.canvasReference.current;
    let ctx = canvas.getContext("2d");

    if (this.backgroundImage.complete) { // This means the image has been loaded.
      canvas.width = this.backgroundImage.width;
      canvas.height = this.backgroundImage.height;
      ctx.drawImage(this.backgroundImage, 0, 0);
    }
  }

  drawThePath = () => {
        if(this.props.data) {
            let ctx1 = this.canvasReference.current.getContext('2d');
            for(var i =0;i<this.props.data.length;i++){
                const startX = this.props.data[i][0][0];
                const startY = this.props.data[i][0][1];
                const endX = this.props.data[i][1][0];
                const endY = this.props.data[i][1][1];
                ctx1.beginPath();
                ctx1.lineWidth = "10";
                ctx1.strokeStyle = "RED";
                ctx1.moveTo(startX,startY);
                ctx1.lineTo(endX,endY);
                ctx1.stroke();
            }
        }
  }

  render() {
    // that's set up to center the canvas for you. See Map.css for more details.
    // Make sure you set up the React references for the canvas correctly so you
    // can get the canvas object and call methods on it.
    return (
      <div className="canvasHolder">
        <canvas ref={this.canvasReference}/>
      </div>
    )
  }

}

export default Map;