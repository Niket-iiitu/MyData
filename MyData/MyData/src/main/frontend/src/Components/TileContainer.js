import React, { useEffect, useState } from 'react';
import DataTile from './DataTile';
import './TileContainer.css';
import { fetchTiles } from '../API/Data.js';

function TileContainer() {
  const [tiles, setTiles] = useState([]);

  useEffect(() => {
    fetchTiles()
      .then(data => {
        setTiles(data);
      })
      .catch(error => {
        alert(error.message); // Show the specific error message
      });
  }, []);

  const handleClick = (tile) => {
    alert(`Clicked on tile: ${tile.title}`);
  };

  return (
    <div className="tile-container">
      {tiles.map((tile, index) => (
        <DataTile key={index} title={tile.title} onClick={() => handleClick(tile)} />
      ))}
    </div>
  );
}

export default TileContainer;
