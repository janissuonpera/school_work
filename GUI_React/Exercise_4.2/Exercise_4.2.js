import React, { Component } from 'react';
import './App.css';
import people from './People';
import AppBar from '@material-ui/core/AppBar';
import ToolBar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';

class App extends Component {

  constructor(props){
    super(props);
    var p = people[0];

    this.state = {
      personList: people,
      listIndex: 0,
      firstName: p.firstName,
      lastName: p.lastName,
      birthTown: p.birthTown,
      birthCountry: p.birthCountry,
      birthYear: p.birthYear
    }
  }

  onChange = name => event => {
    if(name==="birthYear" && isNaN(event.target.value))
      alert("Use only numbers in 'Year' field!")
    else{
      var persons = this.state.personList;
      persons[this.state.listIndex][name] = event.target.value
      this.setState({
        personList: persons,
        [name]: event.target.value
      });
    }
  }

  changePerson = name => event => {
    var plist = this.state.personList;
    var new_index;
    name==="+" ? new_index = this.state.listIndex + 1 : new_index = this.state.listIndex - 1;
    this.setState({
      listIndex: new_index,
      firstName: plist[new_index].firstName,
      lastName: plist[new_index].lastName,
      birthTown: plist[new_index].birthTown,
      birthCountry: plist[new_index].birthCountry,
      birthYear: plist[new_index].birthYear
    });
  }

  render() {

    return (
      <div className="App">

        <AppBar style={{alignItems:"center"}}>
          <ToolBar>
            <Typography variant="h4" color="inherit" style={{flex: 1}}>
              Exercise 4.2
            </Typography>
          </ToolBar>
        </AppBar>

        <div id="names" style={{margin:"auto", marginTop:"75px"}}>
          <h2 style={{marginBottom:"0px"}}>Name</h2>
          <TextField
            id="firstName"
            label="First Name"
            value={this.state.firstName}
            style={{marginTop:"0px"}}
            margin="normal"
            onChange={this.onChange("firstName")}
          />
          &nbsp;
          &nbsp;
          <TextField
            id="lastName"
            label="Last Name"
            value={this.state.lastName}
            style={{marginTop:"0px"}}
            margin="normal"
            onChange={this.onChange("lastName")}
          />
        </div>

        <div id="born" style={{margin:"auto"}}>
          <h2 style={{marginBottom:"0px"}}>Born</h2>

          <TextField
            id="city"
            label="City"
            value={this.state.birthTown}
            style={{marginTop:"0px"}}
            margin="normal"
            onChange={this.onChange("birthTown")}
          />
          &nbsp;
          &nbsp;
          <TextField
            id="country"
            label="Country"
            value={this.state.birthCountry}
            style={{marginTop:"0px"}}
            margin="normal"
            onChange={this.onChange("birthCountry")}
          />
          <br/>
          <TextField
            id="year"
            label="Year"
            value={this.state.birthYear}
            style={{marginTop:"0px"}}
            margin="normal"
            onChange={this.onChange("birthYear")}
          />
        </div>

        <div id="controls" style={{margin:"auto"}}>
          <Button
            variant="contained"
            color="primary"
            style={{marginTop:"10px"}}
            disabled={this.state.listIndex===0}
            onClick={this.changePerson("-")}
          >
            Previous
          </Button>
          &nbsp;
          &nbsp;
          <Button
            variant="contained"
            color="primary"
            style={{marginTop:"10px"}}
            disabled={this.state.listIndex===this.state.personList.length-1}
            onClick={this.changePerson("+")}
          >
            Next
          </Button>
        </div>
      </div>
    );
  }
}

export default App;
