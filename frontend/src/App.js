import React, {Component} from 'react';
import monty from './monty.jpg';
import './App.css';
import BackendHealth from './BackendHealth/BackendHealth';
import SimulatorForm from "./SimulatorTrigger/SimulatorForm";

class App extends Component {
    render() {
        return (
            <div className="App">
                <header className="App-header">
                    <BackendHealth/>
                    <img src={monty} className="App-monty" alt="logo"/>
                    <h1 className="App-title">Welcome to the Monty Hall Simulator</h1>
                    <SimulatorForm/>
                </header>
            </div>
        );
    }
}

export default App;
