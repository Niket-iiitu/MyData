import React, { useEffect, useState } from 'react';
import DataTile from './DataTile';
import DataPopup from './DataPopup';
import './TileContainer.css';
import { fetchTiles, fetchCategories } from '../API/Data.js';

function TileContainer() {
  const [tiles, setTiles] = useState([]);
  const [filteredTiles, setFilteredTiles] = useState([]);
  const [categories, setCategories] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState('All');
  const [selectedTile, setSelectedTile] = useState(null);

  useEffect(() => {
    // Fetch categories
    //TODO: Add Auth ID
    fetchCategories()
      .then(data => setCategories(data))
      .catch(err => alert(err.message));

    // Fetch all tiles
    fetchTiles()
      .then(data => {
        setTiles(data);
        setFilteredTiles(data);
      })
      .catch(error => alert(error.message));
  }, []);

  const handleClick = (tile) => {
    setSelectedTile(tile);
  };

  const closePopup = () => {
    setSelectedTile(null);
  };

  const handleCategoryChange = (e) => {
    const category = e.target.value;
    setSelectedCategory(category);
    fetchTiles(category).then(data => {
      setTiles(data);
      setFilteredTiles(data);
    }).catch(error => alert(error.message));
  };

  return (
    <div>
      <div className="filter-bar">
        <label htmlFor="category-select">Filter by Category: </label>
        <select id="category-select" value={selectedCategory} onChange={handleCategoryChange}>
          {categories.map((cat, index) => (
            <option key={index} value={cat}>{cat}</option>
          ))}
        </select>
      </div>

      <div className="tile-container">
        {filteredTiles.map((tile, index) => (
          <DataTile key={index} title={tile.title} onClick={() => handleClick(tile)} />
        ))}

        {selectedTile && (
          <DataPopup
            title={selectedTile.title}
            category={selectedTile.category}
            tags={selectedTile.tags}
            data={selectedTile.data}
            onClose={closePopup}
          />
        )}
      </div>
    </div>
  );
}

export default TileContainer;
