import React, { Component } from 'react';
import Checkbox from '@material-ui/core/Checkbox';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';

class App extends Component {
  state = {
    c1: false,
    c2: false,
    c3: false,
    c4: false,
    c5: false,
    c6: false,
    c7: false,
    c8: false,
    binary: "00000000",
    decimal: ""
  };

  handleCheck = name => event => {
    var tmp = this.state.binary.split("");
    var i = name.split("")[1] - 1;
    tmp[i] = event.target.checked ? 1 : 0;
    tmp = tmp.join("");

    this.setState({
      binary: tmp,
      [name]: event.target.checked
    });
  };

  convert = name => event => {
    var tmp = parseInt(this.state.binary, 2);
    this.setState({
      decimal: tmp,
    });
  };

  render() {
    return (
      <div className="App" align="center">
        <div className="binaryBoxes">
          <Checkbox
            checked={this.state.c1}
            onChange={this.handleCheck('c1')}
          />
          <Checkbox
            checked={this.state.c2}
            onChange={this.handleCheck('c2')}
          />
          <Checkbox
            checked={this.state.c3}
            onChange={this.handleCheck('c3')}
          />
          <Checkbox
            checked={this.state.c4}
            onChange={this.handleCheck('c4')}
          />
          <Checkbox
            checked={this.state.c5}
            onChange={this.handleCheck('c5')}
          />
          <Checkbox
            checked={this.state.c6}
            onChange={this.handleCheck('c6')}
          />
          <Checkbox
            checked={this.state.c7}
            onChange={this.handleCheck('c7')}
          />
          <Checkbox
            checked={this.state.c8}
            onChange={this.handleCheck('c8')}
          />
        </div>
        <TextField
          variant="outlined"
          value={this.state.binary}
          disabled={true}
          inputProps={{
            style: {
              textAlign: "center",
              fontSize: "150%"
            }
          }}
        />
        <br/>
        <Button
          variant="contained"
          color="primary"
          style={{marginTop:"10px", marginBottom:"10px"}}
          onClick={this.convert()}
        >
          Convert
        </Button>
        <br/>
        <TextField
          variant="outlined"
          value={this.state.decimal}
          disabled={true}
          inputProps={{
            style: {
              textAlign: "center",
              fontSize: "150%"
            }
          }}
        />
      </div>
    );
  }
}

export default App;
