import React, { useState } from 'react';
import './DataPopup.css';
import {updateNote} from '../API/Data.js';

const DataPopup = ({ noteId, title, category, tags, data, categoryList, onClose }) => {
  const [selectedCategory, setSelectedCategory] = useState(category || '');
  const [showAddCategory, setShowAddCategory] = useState(false);
  const [newCategory, setNewCategory] = useState('');
  const [newTitle, setNewTitle] = useState(title);

  const handelButtonClick = (e) => {
    console.log("Update");
    var data = {
        "noteId": noteId,
        "title": title,
        "category": category,
        "data": data,
        "tags": tags.join('|')
    };
    var updateRequired = false;

    if(newCategory!="" && newCategory!=category){
        data.category = newCategory;
        updateRequired = true;
    }

    if(newTitle!=title){
        data.title = newTitle;
        updateRequired = true;
    }

    if(updateRequired){
        updateNote(data).then((message)=> {
            onClose(true);
            alert(message);
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
          <input
            className="popup-title-input"
            type="text"
            value={newTitle}
            onChange={(e) => setNewTitle(e.target.value)}
          />
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
              (tag.length>0) && <span key={index} className="tag-item">{tag}</span>
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
