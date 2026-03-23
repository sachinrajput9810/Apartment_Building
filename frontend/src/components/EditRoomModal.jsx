import { useState } from 'react';

function EditRoomModal({ room, onSave, onClose }) {
    const [roomName, setRoomName] = useState(room.roomName || '');
    const [ownerName, setOwnerName] = useState(room.ownerName || '');
    const [commonRoomType, setCommonRoomType] = useState(room.commonRoomType || 'GYM');

    const handleSubmit = (e) => {
        e.preventDefault();
        const data = { roomName: roomName.trim() };

        if (room.type === 'apartment') {
            data.ownerName = ownerName.trim();
        } else {
            data.commonRoomType = commonRoomType;
        }

        onSave(room.id, data);
    };

    return (
        <div className="modal-overlay" onClick={onClose}>
            <div className="modal" onClick={(e) => e.stopPropagation()}>
                <h3>✏️ Edit {room.roomName}</h3>
                <form onSubmit={handleSubmit}>
                    <div className="form-group">
                        <label htmlFor="edit-room-name">Room Name</label>
                        <input
                            id="edit-room-name"
                            type="text"
                            value={roomName}
                            onChange={(e) => setRoomName(e.target.value)}
                            autoFocus
                            required
                        />
                    </div>

                    {room.type === 'apartment' && (
                        <div className="form-group">
                            <label htmlFor="edit-owner-name">Owner Name</label>
                            <input
                                id="edit-owner-name"
                                type="text"
                                value={ownerName}
                                onChange={(e) => setOwnerName(e.target.value)}
                            />
                        </div>
                    )}

                    {room.type === 'common_room' && (
                        <div className="form-group">
                            <label htmlFor="edit-common-type">Common Room Type</label>
                            <select
                                id="edit-common-type"
                                value={commonRoomType}
                                onChange={(e) => setCommonRoomType(e.target.value)}
                            >
                                <option value="GYM">Gym</option>
                                <option value="LIBRARY">Library</option>
                                <option value="LAUNDRY">Laundry</option>
                            </select>
                        </div>
                    )}

                    <div className="modal-actions">
                        <button type="button" className="btn btn-secondary" onClick={onClose}>Cancel</button>
                        <button type="submit" className="btn btn-primary">Save Changes</button>
                    </div>
                </form>
            </div>
        </div>
    );
}

export default EditRoomModal;
