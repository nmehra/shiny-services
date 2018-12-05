 import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';

class AppNav extends Component {

  render() {
    
    return (
      <header className="App-header">
          <img src={logo} className="App-logo" alt="logo" />
          <h1 className="App-title">Welcome to Chaos</h1>
       </header>
    );
  }
}

export default AppNav;



