import React, {Component} from 'react';
import "./Map.css";
import TextField from '@material-ui/core/TextField';

class BuildingPicker extends Component {
    render(){
        return (
              <div className="center-text">
                <p> Pick {this.props.startORend} Building:</p>
                <TextField
                  placeholder= "Building abbrv."
                  id={this.props.id}
                  label={this.props.label}
                  value={this.props.value}
                  onChange={this.props.onChange}
                  type="text"
                  className={this.props.className}
                  InputLabelProps={{
                    shrink: true,
                  }}
                  autoFocus
                />
              </div>
            );
    }
}
 export default BuildingPicker;