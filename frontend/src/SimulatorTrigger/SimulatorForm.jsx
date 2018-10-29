import React, {Component} from "react";
import axios from 'axios';
import {Form} from 'antd';
import 'antd/lib/form/style/index.css';

const errorStyle = {
    color: 'red'
};

class SimulatorForm extends Component {

    state = {
        nbrOfDoors: 3,
        nbrOfSimulations: 100,
        result: 0,
        error: ''
    };

    handleNbrOfDoorsChange = event => {
        this.setState({nbrOfDoors: event.target.value})
    }
    handleNbrOfSimulations = event => {
        this.setState({nbrOfSimulations: event.target.value})
    }

    formSubmit = e => {
        e.preventDefault();
        this.setState({error: ''})
        axios.post('http://localhost:8080/simulate',
            {
                nbrOfDoors: this.state.nbrOfDoors,
                nbrOfSimulations: this.state.nbrOfSimulations
            },)
            .then(res => {
                console.log(res);
                console.log(res.data);
                this.setState({result: res.data.winPercentage});
            })
            .catch(res => {
                this.setState({error: res.toString() + "(Check server logs)", result: -1});
            });
    }

    render() {
        return (
            <Form onSubmit={this.formSubmit}>
                <Form.Item>
                    <label>Number of doors: <input type="text" value={this.state.nbrOfDoors} name="this.state.nbrOfDoors"
                                                   onChange={this.handleNbrOfDoorsChange}/></label>
                    <label>Number of simulations: <input type="text" value={this.state.nbrOfSimulations} name="this.state.nbrOfSimulations"
                                                         onChange={this.handleNbrOfSimulations}/></label><br/>
                    <label>Win%: {this.state.result}</label><br/>
                    <button type="submit">Start Simulation</button><br/>
                    <label style={errorStyle}>{this.state.error}</label><br/>
                </Form.Item>
            </Form>
        );
    }
}

export default SimulatorForm;