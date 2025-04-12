import './App.css';
import DataTile from './Components/DataTile.js';
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

function tileClick(){
    console.log("Tile clicked");
}

function App() {
  serverHealthCheck();
  return (
    <div className="App">
      <div>Hellow</div>
      <div>
        <DataTile title="Content1" onClick={tileClick}/>
        <DataTile title="Content2" onClick={tileClick}/>
      </div>
    </div>
  );
}

export default App;
