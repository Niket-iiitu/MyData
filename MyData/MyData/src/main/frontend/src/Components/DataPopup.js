import React, { useState } from 'react';
import './DataPopup.css';
import {updateNote, createNote, deleteNote} from '../API/Data.js';
import {generateSummery} from '../API/AI.js';

const DataPopup = ({ noteId, title, category, tags, data, categoryList, onClose }) => {
  const [selectedCategory, setSelectedCategory] = useState(category || 'Default');
  const [showAddCategory, setShowAddCategory] = useState(false);
  const [newCategory, setNewCategory] = useState('');
  const [newTitle, setNewTitle] = useState(title);
  const [newData, setNewData] = useState(data);
  const [newTags, setNewTags] = useState(tags);

  const handelSummarize = (e) => {
    console.log("Summarize Note");
    generateSummery(newData).then((response)=>{
        if(response.summary!=null && response.summary!=""){
            setNewData(response.summary);
        }
        if(response.title!=null && response.title!=""){
            setNewTitle(response.title);
        }
        if(Array.isArray(response.tags) && response.tags.length > 0){
            setNewTags(response.tags);
        }
    })
  }

  const handelCancel = (e) => {
    console.log("Canceling note");
    deleteNote(noteId).then((message)=> {
        onClose(true);
        alert(message);
    }).catch(err => alert(err.message));
  }

  const handelUpdate = (e) => {
    console.log("Updating note");
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

    if(newTags.join('|')!=tags.join('|')){
        data.tags = newTags.join('|');
        updateRequired = true;
    }

    if(newData!=data){
        data.data = newData;
        updateRequired = true;
    }

    if(updateRequired){
        updateNote(data).then((message)=> {
            onClose(true);
            alert(message);
        }).catch(err => alert(err.message));
    }
  }

  const handelCreate = (e) => {
      console.log("New note created");
      var data = {
          "noteId": noteId,
          "title": newTitle,
          "category": category,
          "data": newData,
          "tags": newTags.join('|')
      };
      var validRequest = true;

      if(newCategory!="" && newCategory!=category){
        data.category = newCategory;
      }

      if(newTitle.trim()!=""){
        validRequest = validRequest && true;
      }
      else{
        validRequest = validRequest && false;
      }

      if(validRequest){
          createNote(data).then((message)=> {
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
              {newTags.map((tag, index) => (
                (tag.length > 0) && (
                  <span key={index} className="tag-item">
                    {tag}
                    <button
                      className="delete-tag-button"
                      onClick={() => {
                        const updatedTags = tags.filter((_, i) => i !== index);
                        setNewTags(updatedTags); // Update the tags state
                      }}
                    >
                      ×
                    </button>
                  </span>
                )
              ))}
              <input
                type="text"
                className="add-tag-input"
                placeholder="Add a tag"
                onKeyDown={(e) => {
                  if (e.key === 'Enter' && e.target.value.trim() !== '') {
                    setNewTags([...newTags, e.target.value.trim()]); // Add new tag
                    e.target.value = ''; // Clear input
                  }
                }}
              />
            </div>
        </div>

        <div className="popup-content">
          <textarea
            className="popup-textarea"
            value={newData}
            onChange={(e) => setNewData(e.target.value)}
          />
        </div>
        <div className="popup-buttons">
            {noteId === "" ? (
              <>
                  <button type="button" className="create-button" onClick={handelCreate}>
                    Create
                  </button>
                  <button type="button" className="summarize-button" onClick={handelSummarize}>
                    Summarize
                  </button>
              </>
            ) : (
              <>
                  <button type="button" className="delete-button" onClick={handelCancel}>
                    Delete
                  </button>
                  <button type="button" className="update-button" onClick={handelUpdate}>
                    Update
                  </button>
              </>
            )}
        </div>
      </div>
    </div>
  );
};

export default DataPopup;
