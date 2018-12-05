import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import {Link} from 'react-router-dom';
import { Button } from 'reactstrap';
import AppNav from './AppNav';

class Home extends Component {
 
 
  render() {
    
    return (
      <div className="App">
        <AppNav />
        <div className="App-intro">
          <h2>Users List</h2>
          <Button size="sm" color="primary" tag={Link} to="/users/">Users</Button>
         
        </div>
      </div>
    );
  }
}

export default Home;
