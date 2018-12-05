import React, { Component } from 'react';
import {BrowserRouter as Router, Switch, Route} from 'react-router-dom'; 
import Users from './Users';
import Home from './home';
import UserEdit from './UserEdit';
import AccountEdit from './AccountEdit';
import Transfer from './Transfer';

class App extends Component {
  
  render() {
    
    return (
      <Router>
        <Switch>
          <Route path='/' exact={true} component={Home}/>
          <Route path='/users' exact={true} component={Users}/>
          <Route path='/users/:id' component={UserEdit}/>
          <Route path='/accounts/:id' component={AccountEdit}/>
          <Route path='/transfer/:id' component={Transfer}/>
        </Switch>
      </Router>
    );
  }
}

export default App;
