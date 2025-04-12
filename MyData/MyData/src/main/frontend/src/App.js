import './App.css';
import DataTile from './Components/DataTile.js';

function serverHealthCheck(){
    console.log("Server OK");
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
