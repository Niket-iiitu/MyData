import React from 'react';
import './CSS/DataTile.css';

function DataTile({ title, onClick }) {
  return (
    <div className="data-tile" onClick={onClick}>
      {title}
    </div>
  );
}

export default DataTile;
