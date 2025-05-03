import React, { useEffect, useState } from 'react';
import DataTile from './DataTile';
import DataPopup from './DataPopup';
import TagModal from './TagModel';
import './TileContainer.css';
import { fetchTiles, fetchCategories } from '../API/Data.js';
import { logOut } from '../API/Authentication.js';

function TileContainer({ onLogout }) {
  const [tiles, setTiles] = useState([]);
  const [filteredTiles, setFilteredTiles] = useState([]);
  const [categories, setCategories] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState('Default');
  const [selectedTile, setSelectedTile] = useState(null);
  const [selectedTags, setSelectedTags] = useState([]);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [tagList, setTagList] = useState(["Test1", "Test2", "Test3"]);
  const [createNote, setCreateNote] = useState(false);

  const handleModalSave = (tags) => {
    setSelectedTags(tags);
    setIsModalOpen(false);
  };

  const togglePopup = () => {
    setCreateNote(!createNote);
    if(createNote===true){
        setSelectedTile(null);
        fetchCategories()
            .then(data => setCategories(data))
            .catch(err => alert(err.message));
        fetchTiles(selectedCategory).then(data => {
            setTiles(data);
            setFilteredTiles(data);
        }).catch(error => alert(error.message));
    }
  }

  const handelLogout = () => {
    const uid = localStorage.getItem("IdeaNotesUid");
    const sessionId = localStorage.getItem("IdeaNotesSessionId");
    logOut(uid, sessionId).then(res => {
        if(res.status === "LOGGED_OUT"){
            localStorage.removeItem("IdeaNotesUid");
            localStorage.removeItem("IdeaNotesSessionId");
            onLogout();
        }
        else{
            alert(res.message);
        }
    }).catch(err => alert(err.message));
  }

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
        const tagSet = new Set();

        for (const note of data) {
          const tags = note.tags;
          if (Array.isArray(tags) && tags.some(tag => tag.trim() !== "")) {
            tags.forEach(tag => {
              if (tag.trim() !== "") {
                tagSet.add(tag.trim());
              }
            });
          }
        }

        const sortedTags = Array.from(tagSet).sort();
        setTagList(sortedTags);
      })
      .catch(error => {
        alert(error.message);
        console.log(error);
      });
  }, []);

  useEffect(() => {
    if(selectedTags.length>0){
        const newTiles = tiles.filter(tile =>
            selectedTags.every(tag => tile.tags.includes(tag))
        );
        setFilteredTiles(newTiles);
    }
    else{
       setFilteredTiles(tiles);
    }
  }, [selectedTags, tiles]);

  const handleClick = (tile) => {
    setSelectedTile(tile);
  };

  const closePopup = (update=false) => {
    if(update){
        setSelectedTile(null);
        fetchCategories()
            .then(data => setCategories(data))
            .catch(err => alert(err.message));
        fetchTiles(selectedCategory).then(data => {
            setTiles(data);
            setFilteredTiles(data);
        }).catch(error => alert(error.message));
    }
    else{
        setSelectedTile(null);
    }
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
    <div className="mainTileContainer">
      <div className="filter-bar">
        <select id="category-select" value={selectedCategory} onChange={handleCategoryChange}>
          {categories.map((cat, index) => (
            <option key={index} value={cat}>{cat}</option>
          ))}
        </select>
        <div>
          <div
            className="tag-field"
            onClick={() => setIsModalOpen(true)}
          >
            {selectedTags.length === 0 ? (
              <span className="placeholder">Tags</span>
            ) : (
              selectedTags.map((tag, idx) => (
                <span key={idx} className="tag-chip">
                  {tag}
                </span>
              ))
            )}
          </div>

          {isModalOpen && (
            <TagModal
              listOfTags = {tagList}
              selectedTags={selectedTags}
              onSave={handleModalSave}
              onClose={() => setIsModalOpen(false)}
            />
          )}
        </div>
        <div>
          <button onClick={handelLogout} className="logout-button">Logout</button>
        </div>
      </div>
      <div className="tile-container">
        {filteredTiles.map((tile, index) => (
          <DataTile key={index} title={tile.title} onClick={() => handleClick(tile)} />
        ))}
        {selectedTile && (
          <DataPopup
            noteId={selectedTile.id}
            title={selectedTile.title}
            category={selectedTile.category}
            tags={selectedTile.tags}
            data={selectedTile.data}
            categoryList={categories}
            onClose={closePopup}
          />
        )}
      </div>
      <div>
        <button className="floating-create-btn" onClick={togglePopup}>
          ＋
        </button>
        {createNote && (
          <DataPopup
            noteId=""
            title="New Idea"
            category={selectedCategory}
            tags={[]}
            data=""
            categoryList={categories}
            onClose={togglePopup}
          />
        )}
      </div>
    </div>
  );
}

export default TileContainer;
