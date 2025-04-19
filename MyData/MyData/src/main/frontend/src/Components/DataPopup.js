import React, { useState } from 'react';
import './DataPopup.css';
import {updateNote} from '../API/Data.js';

const DataPopup = ({ noteId, title, category, tags, data, categoryList, onClose }) => {
  const [selectedCategory, setSelectedCategory] = useState(category || '');
  const [showAddCategory, setShowAddCategory] = useState(false);
  const [newCategory, setNewCategory] = useState('');

  const handelButtonClick = (e) => {
    console.log("Update");
    var data = {
        "noteId": noteId,
        "title": title,
        "category": category,
        "data": data,
        "tags": tags.join('|')
    };
    if(newCategory!="" && newCategory!=category){
        data.category = newCategory;
        updateNote(data).then((message)=> {
            alert(message);
            onClose(true);
        }).catch(err => alert(err.message));
    }
  }

  const handleCategoryChange = (e) => {
    const selected = e.target.value;
    setSelectedCategory(selected);

    if (selected === 'add') {
        setNewCategory("");
        setShowAddCategory(true);
    } else {
        setNewCategory(selected);
        setShowAddCategory(false);
    }
  };

  return (
    <div className="popup-overlay" onClick={() => onClose(false)}>
      <div className="popup-container" onClick={(e) => e.stopPropagation()}>
        <div className="popup-header">
          <h2>{title}</h2>
          <button className="close-button" onClick={() => onClose(false)}>×</button>
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
        <div className="popup-buttons">
            <button type="button" className="update-button" onClick={handelButtonClick}>
                Update
            </button>
        </div>
      </div>
    </div>
  );
};

export default DataPopup;
