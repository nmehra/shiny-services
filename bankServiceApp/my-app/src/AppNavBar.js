import React, {Component} from 'react';
import './App.css';

class AppNavBar extends Component {
	render() {
		return (
			<nav role="full-horizontal">
			  <ul>
			    <li><a href="#">Users</a></li>
			    <li><a href="#">Accounts</a></li>
			    <li><a href="#">Transactions</a></li>
			    <li><a href="#">About</a></li>
			    <li><a href="#">Contact</a></li>
			  </ul>
			</nav>​
			)
	}
}