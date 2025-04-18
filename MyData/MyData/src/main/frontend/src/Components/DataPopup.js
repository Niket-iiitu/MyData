import React, { useState } from 'react';
import './DataPopup.css';

const DataPopup = ({ title, category, tags, data, categoryList, onClose }) => {
  const [selectedCategory, setSelectedCategory] = useState(category || '');
  const [showAddCategory, setShowAddCategory] = useState(false);
  const [newCategory, setNewCategory] = useState('');

  const handleCategoryChange = (e) => {
    const selected = e.target.value;
    setSelectedCategory(selected);

    if (selected === 'add') {
      setShowAddCategory(true);
    } else {
      setShowAddCategory(false);
    }
  };

  return (
    <div className="popup-overlay" onClick={onClose}>
      <div className="popup-container" onClick={(e) => e.stopPropagation()}>
        <div className="popup-header">
          <h2>{title}</h2>
          <button className="close-button" onClick={onClose}>×</button>
        </div>
        <div className="popup-meta">
          <select
            id="category-select-popup"
            value={selectedCategory}
            onChange={handleCategoryChange}
            className="category-dropdown"
          >
            {categoryList.map((cat, index) => (
              <option key={index} value={cat}>{cat}</option>
            ))}
            <option value="add">Add Category</option>
          </select>

          {showAddCategory && (
            <input
              type="text"
              className="add-category-input"
              placeholder="Enter new category"
              value={newCategory}
              onChange={(e) => setNewCategory(e.target.value)}
            />
          )}

          <div className="tags-box">
            {tags.map((tag, index) => (
              <span key={index} className="tag-item">{tag}</span>
            ))}
          </div>
        </div>

        <div className="popup-content">
          {data}
        </div>
      </div>
    </div>
  );
};

export default DataPopup;
