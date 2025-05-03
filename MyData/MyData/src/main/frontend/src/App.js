import React, { useState } from 'react';
import './App.css';
import TileContainer from './Components/TileContainer.js';
import Login from './Components/Login.js';
import {getPing} from './API/HealthCheck.js';

async function serverHealthCheck() {
    try {
        const response = await getPing();
        if (!response) {
            alert('Unable to connect to the server');
        }
    } catch (error) {
        alert('Unable to connect to the server');
        console.error('Ping failed:', error);
    }
}

function App() {
    const [authenticated, setAuthenticated] = useState(false);

    serverHealthCheck();
    return (
        <div className="App">
            <div className="app-header">
                <span>IdeaNotes</span>
                <img src="/LightBulb.png" alt="Lightbulb" className="lightbulb-icon" />
            </div>
            {authenticated ? (
                <TileContainer/>
            ) : (
                <Login onLogin={(user) => {
                    setAuthenticated(true);
                }}/>
            )

            }
        </div>
    );
}

export default App;
