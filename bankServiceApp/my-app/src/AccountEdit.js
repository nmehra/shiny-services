import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, FormUser, Input, Label } from 'reactstrap';
import AppNav from './AppNav';

class AccountEdit extends Component {

  emptyItem = {
    accountId:'',
    ifscCode:'',
    name: '',
    address: '',
    city: '',
    stateOrProvince: '',
    country: '',
    postalCode: '',
    balance:''
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
    if (this.props.match.params.id !== 'new') {
      const account = await (await fetch(`/api/account/${this.props.match.params.id}`)).json();
      this.setState({item: account});
    }
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

    await fetch('/api/account/'+item.id, {
      method: (item.id) ? 'PUT' : 'POST',
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
    const title = <h2>{item.id ? 'Edit Account' : 'Add Account'}</h2>;

    return <div className="App">

      <AppNav/>
      <Container>
        {title}
        <Form onSubmit={this.handleSubmit}>
           <FormGroup>
            <Label for="accountId">Account Id</Label>
            <Input type="text" name="accountId" id="accountId" value={item.accountId || ''}
                   onChange={this.handleChange} />
          </FormGroup>
          <FormGroup>
            <Label for="ifscCode">IFSC Code</Label>
            <Input type="text" name="ifscCode" id="ifscCode" value={item.ifscCode || ''}
                   onChange={this.handleChange} />
          </FormGroup>
          <FormGroup>
            <Label for="name">Name</Label>
            <Input type="text" name="name" id="name" value={item.name || ''}
                   onChange={this.handleChange} />
          </FormGroup>
          <FormGroup>
            <Label for="address">Address</Label>
            <Input type="text" name="address" id="address" value={item.address || ''}
                   onChange={this.handleChange} />
          </FormGroup>
          <FormGroup>
            <Label for="city">City</Label>
            <Input type="text" name="city" id="city" value={item.city || ''}
                   onChange={this.handleChange} />
          </FormGroup>
          <div className="row">
            <FormGroup className="col-md-4 mb-3">
              <Label for="stateOrProvince">State/Province</Label>
              <Input type="text" name="stateOrProvince" id="stateOrProvince" value={item.stateOrProvince || ''}
                     onChange={this.handleChange} />
            </FormGroup>
            <FormGroup className="col-md-5 mb-3">
              <Label for="country">Country</Label>
              <Input type="text" name="country" id="country" value={item.country || ''}
                     onChange={this.handleChange} />
            </FormGroup>
            <FormGroup className="col-md-3 mb-3">
              <Label for="postalCode">Postal Code</Label>
              <Input type="text" name="postalCode" id="postalCode" value={item.postalCode || ''}
                     onChange={this.handleChange} />
            </FormGroup>
             </div>
            <FormGroup >
              <Label for="balance">Balance</Label>
              <Input type="text" name="balance" id="balance" value={item.balance || ''}
                     onChange={this.handleChange} />
            </FormGroup>
         

          <FormGroup>
            
            <Button color="primary" type="submit">Save</Button>
            <Button color="secondary" tag={Link} to={'/users'}>Cancel</Button>
            <Button color="primary" tag={Link} to={'/transfer/'+item.id}>Transfer</Button>
          </FormGroup>
        </Form>
      </Container>
    </div>
  }
}

export default withRouter(AccountEdit);
