import React, { useState } from "react";
import "./TagModel.css"; // Use same CSS file for modal styles

const allTags = ["Frontend", "Backend", "Fullstack", "DevOps", "Design", "AI"];

const TagModal = ({ selectedTags, onSave, onClose }) => {
  const [tempTags, setTempTags] = useState(selectedTags);

  const toggleTag = (tag) => {
    if (tempTags.includes(tag)) {
      setTempTags(tempTags.filter((t) => t !== tag));
    } else {
      setTempTags([...tempTags, tag]);
    }
  };

  return (
    <div className="modal-overlay">
      <div className="modal-container">
        <h2>Select Tags</h2>
        <div className="tag-options">
          {allTags.map((tag) => (
            <button
              key={tag}
              className={`tag-option ${tempTags.includes(tag) ? "selected" : ""}`}
              onClick={() => toggleTag(tag)}
            >
              {tag}
            </button>
          ))}
        </div>
        <div className="modal-buttons">
          <button onClick={onClose} className="btn cancel">Cancel</button>
          <button onClick={() => onSave(tempTags)} className="btn confirm">OK</button>
        </div>
      </div>
    </div>
  );
};

export default TagModal;
