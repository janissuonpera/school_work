import React, { Component } from 'react';
import './App.css';
import AppBar from '@material-ui/core/AppBar'
import ToolBar from '@material-ui/core/Toolbar'
import Typography from '@material-ui/core/Typography'
import Menu from '@material-ui/core/Menu'
import MenuItem from '@material-ui/core/MenuItem'
import IconButton from '@material-ui/core/IconButton';
import Button from '@material-ui/core/Button'
import Population from './Person_js/Population'

class App extends Component {

  constructor(props){
    super(props);
    var pop = new Population("UK");
    var new_person = pop.getPerson();
    this.state = {
      population: pop,
      person: new_person,
      anchorEl: null,
    }
  }

  //==================Population and person methods=================
  prevPerson = () =>{
    this.setState({
      person: this.state.population.previousPerson()
    });
  }

  nextPerson = () =>{
    this.setState({
      person: this.state.population.nextPerson()
    });
  }
  //=================================================================
  //=====================Menu methods ======================
  handleMenuOpen = event => {

    this.setState({
      anchorEl: event.currentTarget
    });
  }

  handleMenuClose = () =>{
    this.setState({
      anchorEl: null
    });
  }

  //==================================================================
  render() {
    var { anchorEl, person } = this.state;
    return (
      <div className="App">
        <AppBar style={{alignItems:"center"}}>
          <ToolBar>
            <IconButton
              className="Menu"
              color="inherit"
              aria-owns={anchorEl ? 'main_menu' : undefined}
              aria-haspopup="true"
              onClick={this.handleMenuOpen}
            >
              View
            </IconButton>
            <Menu
              id="main_menu"
              anchorEl={anchorEl}
              open={Boolean(anchorEl)}
              onClose={this.handleMenuClose}
            >
              <MenuItem onClick={this.nextPerson}>Next</MenuItem>
              <MenuItem onClick={this.prevPerson}>Previous</MenuItem>
            </Menu>

            <Typography variant="h4" color="inherit" style={{flex: 1}}>
              Exercise 2.2
            </Typography>
          </ToolBar>
        </AppBar>
        <div style={{marginTop:"65px"}}>
            First name: {person.firstName}
            <br/>
            Last name: {person.lastName}
            <br/>
            Birth town: {person.birthTown}
            <br/>
            Birth Year: {person.birthYear}
        </div>
      </div>
    );
  }
}

export default App;
