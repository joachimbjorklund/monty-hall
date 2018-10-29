import React, {Component} from "react";
import axios from 'axios';
import {Form} from 'antd';
import 'antd/lib/form/style/index.css';

import './SimulatorTrigger.css';

class SimulatorTrigger extends Component {

    state = {
        nbrOfDoors: '3',
        nbOfSimulations: '100'
    };

    handleNbrOfDoorsChange = event => {
        this.setState({nbrOfDoors: event.target.value})
    }
    handleNbrOfSimulations = event => {
        this.setState({nbOfSimulations: event.target.value})
    }

    handleSubmit = event => {
        // event.preventDefault();

        axios.post('http://localhost:8080/start',
            {
                nbrOfDoors: this.state.nbrOfDoors,
                nbOfSimulations: this.state.nbOfSimulations
            },)
            .then(res => {
                console.log(res);
                console.log(res.data);
            })
    }

    render() {
        return (
            <Form onSubmit={this.handleSubmit()}>
                <Form.Item>
                    <label>Number of doors: <input type="text" name="this.state.nbrOfDoors"
                                                   onChange={this.handleNbrOfDoorsChange}/></label>
                </Form.Item>
                <Form.Item>
                    <label>Number of simulations: <input type="text" name="this.state.nbOfSimulations"
                                                         onChange={this.handleNbrOfSimulations}/></label><br/>
                </Form.Item>
                <button type="submit">Start Simulation</button>
            </Form>
        );
    }

    getHealth = () => {
        axios.get(`health`)
            .then(res => {
                if (res.status === 200) {
                    this.setState({backendHealth: res.data});
                } else {
                    this.setState({backendHealth: "DOWN"});
                }
            })
            .catch(res => {
                console.log(res);
                this.setState({backendHealth: "DOWN"});
            });
    }

}

export default SimulatorTrigger;