import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import AppBar from '@material-ui/core/AppBar'
import ToolBar from '@material-ui/core/Toolbar'
import Typography from '@material-ui/core/Typography'
import MenuIcon from '@material-ui/icons/Menu'
import Menu from '@material-ui/core/Menu'
import MenuItem from '@material-ui/core/MenuItem'
import IconButton from '@material-ui/core/IconButton';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';
import Button from '@material-ui/core/Button'

//Used npm install '@material-ui/core' and '@material-ui/icons'

class App extends Component {
  state = {
    anchorEl: null,
    dialogOpen: false
  }

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

  nextPage = () => {
    window.location.href = "http://tuni.fi";
  }

  handleDialogOpen = () => {
    this.setState({ dialogOpen: true });
  };

  handleDialogClose = () => {
    this.setState({ dialogOpen: false });
  };

  render() {
    const { anchorEl } = this.state;
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
              <MenuIcon/>
            </IconButton>
            <Menu
              id="main_menu"
              anchorEl={anchorEl}
              open={Boolean(anchorEl)}
              onClose={this.handleMenuClose}
            >
              <MenuItem onClick={this.handleDialogOpen}>Next page</MenuItem>
            </Menu>
            <Dialog
              open={this.state.dialogOpen}
              onClose={this.handleDialogClose}
              aria-labelledby="alert-dialog-title"
              aria-describedby="alert-dialog-description"
            >
              <DialogTitle id="alert-dialog-title">{"Proceed to next page?"}</DialogTitle>
              <DialogContent>
                <DialogContentText id="alert-dialog-description">
                  Goes to http://tuni.fi
                </DialogContentText>
              </DialogContent>
              <DialogActions>
                <Button onClick={this.handleDialogClose} color="primary">
                  Disagree
                </Button>
                <Button onClick={this.nextPage} color="primary" autoFocus>
                  Agree
                </Button>
              </DialogActions>
            </Dialog>
            <Typography variant="h4" color="inherit">
              Exercise 2.1
            </Typography>
          </ToolBar>
        </AppBar>
      </div>
    );
  }
}

export default App;
