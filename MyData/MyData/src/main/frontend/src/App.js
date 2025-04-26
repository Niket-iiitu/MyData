import './App.css';
import TileContainer from './Components/TileContainer.js';
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
    serverHealthCheck();
    return (
        <div className="App">
            <div className="app-header">
                <span>IdeaNotes</span>
                <img src="/LightBulb.png" alt="Lightbulb" className="lightbulb-icon" />
            </div>
            <TileContainer/>
        </div>
    );
}

export default App;
