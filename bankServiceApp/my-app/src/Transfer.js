import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, FormUser, Input, Label } from 'reactstrap';
import AppNav from './AppNav';

class Transfer extends Component {

  emptyItem = {
    fromAccountId:'',
    toAccountId:'',
    toIfscCode:'',
    balance:'',
    amount:''
  };

  constructor(props) {
    super(props);
    this.state = {
      item: this.emptyItem
    };
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  async componentDidMount() {
      const account = await (await fetch(`/api/account/${this.props.match.params.id}`)).json();
      this.setState({item: { fromAccountId: this.props.match.params.id, balance:account.balance}});
  }

  handleChange(event) {
    const target = event.target;
    const value = target.value;
    const name = target.name;
    let item = {...this.state.item};
    item[name] = value;
    this.setState({item});
  }

  async handleSubmit(event) {
    event.preventDefault();
    const {item} = this.state;
    item.delay = window.delay;
    item.useHystrix = window.useHystrix;

    await fetch('/api/transfer/', {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(item),
    });
    //this.props.history.push('/users/${t');
  }

  render() {
    const {item} = this.state;
    const title = <h2>Transfer Amount</h2>;

    return <div className="App">

      <AppNav/>
      <Container>
        {title}
        <Form onSubmit={this.handleSubmit}>
        <div className="row">
           <FormGroup  className="col-md-6 mb-3">
            <Label for="fromAccountId">From Account Id</Label>
             <Input type="text" disabled name="fromAccountId" id="fromAccountId" value={item.fromAccountId || ''}
                   onChange={this.handleChange} />
          </FormGroup>
          <FormGroup className="col-md-6 mb-3">
              <Label for="balance">Balance</Label>
               <Input type="text" disabled name="balance" id="balance" value={item.balance || ''}
                   onChange={this.handleChange} />
            </FormGroup>
        </div>
          <FormGroup>
           <Label for="toAccountId">To Account Id</Label>
            <Input type="text" name="toAccountId" id="toAccountId" value={item.toAccountId || ''}
                   onChange={this.handleChange} />
          </FormGroup>
          <FormGroup>
            <Label for="ifscCode">IFSC Code</Label>
            <Input type="text" name="ifscCode" id="ifscCode" value={item.ifscCode || ''}
                   onChange={this.handleChange} />
          </FormGroup>
          <FormGroup>
            <Label for="amount">Amount</Label>
            <Input type="text" name="amount" id="amount" value={item.amount || ''}
                   onChange={this.handleChange} />
          </FormGroup>

          <FormGroup>
            
            <Button color="primary" type="submit">Transfer</Button>
            <Button color="secondary" tag={Link} to={'/users'}>Cancel</Button>
          </FormGroup>
        </Form>
      </Container>
    </div>
  }
}

export default withRouter(Transfer);
