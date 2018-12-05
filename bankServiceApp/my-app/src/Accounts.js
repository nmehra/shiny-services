import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import logo from './logo.svg';
import './App.css';

import {Link} from 'react-router-dom';
class Accounts extends Component {

  constructor(props) {
    super(props);
    this.state = {accounts: [], isLoading: true};
    this.remove = this.remove.bind(this);
    this.accountsCall = this.accountsCall.bind(this);
  }

  accountsCall(props){
    this.setState({isLoading: true});
    if ( !props.userId) return;
    let userId = props.userId;
    fetch('/api/accounts?userId=' + userId)
      .then(response => response.json())
      .then(data => this.setState({accounts: data, isLoading: false}));
  }
  componentDidMount() {
   this.accountsCall(this.props);
  }

 componentWillReceiveProps(nextProps) {
    this.accountsCall(nextProps);
  }

  async remove(id) {
    await fetch(`/api/accounts/${id}`, {
      method: 'DELETE',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      }
    }).then(() => {
      let updatedAccounts = [...this.state.accounts].filter(i => i.id !== id);
      this.setState({users: updatedAccounts});
    });
  }

  render() {
    const {accounts, isLoading} = this.state;

    if (isLoading) {
      return <p>Loading...</p>;
    }

    const accountList = accounts.map(account => {
      const address = `${account.address || ''} ${account.city || ''} ${account.stateOrProvince || ''}`;
      return <tr key={account.id}>
        <td style={{whiteSpace: 'nowrap'}}>{account.accountId}</td>
        <td>{address}</td>
        <td>{account.balance}</td>
        <td>
          <ButtonGroup>
            <Button size="sm" color="primary" tag={Link} to={"/accounts/" + account.id}>Edit Account</Button>
          </ButtonGroup>
        </td>
      </tr>
    });

    return (
      <div className="App">
        <Container fluid>
          <div className="float-right">
            <Button color="success" tag={Link} to="/accounts/new">Add Account</Button>
          </div>
          <h4>Accounts</h4>
          <Table className="mt-4">
            <thead>
            <tr>
              <th width="20%">Name</th>
              <th width="20%">Location</th>
              <th>Events</th>
              <th width="10%">Balance</th>
            </tr>
            </thead>
            <tbody>
            {accountList}
            </tbody>
          </Table>
        </Container>
      </div>
    );
  }
}

export default Accounts;