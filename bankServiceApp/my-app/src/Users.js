import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import logo from './logo.svg';
import './App.css';
import AppNav from './AppNav';

import {Link} from 'react-router-dom';
class Users extends Component {

  constructor(props) {
    super(props);
    this.state = {users: [], isLoading: true};
    this.remove = this.remove.bind(this);
  }

  componentDidMount() {
    this.setState({isLoading: true});

    fetch('/api/users')
      .then(response => response.json())
      .then(data => this.setState({users: data, isLoading: false}));
  }

  async remove(id) {
    await fetch(`/api/user/${id}`, {
      method: 'DELETE',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      }
    }).then(() => {
      let updatedUsers = [...this.state.users].filter(i => i.id !== id);
      this.setState({users: updatedUsers});
    });
  }

  render() {
    const {users, isLoading} = this.state;

    if (isLoading) {
      return <p>Loading...</p>;
    }

    const userList = users.map(user => {
      const address = `${user.address || ''} ${user.city || ''} ${user.stateOrProvince || ''}`;
      return <tr key={user.id}>
        <td style={{whiteSpace: 'nowrap'}}>{user.name}</td>
        <td>{address}</td>
        <td>{user.accounts.map(account => {
          return <div key={account.id}>{account.title}</div>
        })}</td>
        <td>
          <ButtonGroup>
            <Button size="sm" color="primary" tag={Link} to={"/users/" + user.id}>Edit User</Button>
          </ButtonGroup>
        </td>
      </tr>
    });

    return (
      <div className="App">
        <AppNav />
        <Container fluid>
          <div className="float-right">
            <Button color="success" tag={Link} to="/users/new">Add User</Button>
          </div>
          <h3>Users</h3>
          <Table className="mt-4">
            <thead>
            <tr>
              <th width="20%">Name</th>
              <th width="20%">Location</th>
              <th>Events</th>
              <th width="10%">Actions</th>
            </tr>
            </thead>
            <tbody>
            {userList}
            </tbody>
          </Table>
        </Container>
      </div>
    );
  }
}

export default Users;