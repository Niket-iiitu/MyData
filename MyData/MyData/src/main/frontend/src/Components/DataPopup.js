import React from 'react';
import './DataPopup.css';

const DataPopup = ({ title, category, tags, data, onClose }) => {
  return (
    <div className="popup-overlay" onClick={onClose}>
      <div className="popup-container" onClick={(e) => e.stopPropagation()}>
        <div className="popup-header">
          <h2>{title}</h2>
          <button className="close-button" onClick={onClose}>×</button>
        </div>
        <div className="popup-meta">
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
