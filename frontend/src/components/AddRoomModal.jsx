import { useState } from 'react';

function AddRoomModal({ type, onAdd, onClose }) {
    const [roomName, setRoomName] = useState('');
    const [ownerName, setOwnerName] = useState('');
    const [commonRoomType, setCommonRoomType] = useState('GYM');
    const [error, setError] = useState(null);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError(null);
        if (!roomName.trim()) {
            setError('Room name is required');
            return;
        }

        try {
            if (type === 'apartment') {
                await onAdd('apartment', { roomName: roomName.trim(), ownerName: ownerName.trim() });
            } else {
                await onAdd('common_room', { roomName: roomName.trim(), commonRoomType });
            }
        } catch (err) {
            setError(err.message || 'Failed to add room');
        }
    };

    return (
        <div className="modal-overlay" onClick={onClose}>
            <div className="modal" onClick={(e) => e.stopPropagation()}>
                <h3>{type === 'apartment' ? '🏠 Add Apartment' : '🏛️ Add Common Room'}</h3>
                
                {error && <div className="error-message">{error}</div>}

                <form onSubmit={handleSubmit}>
                    <div className="form-group">
                        <label htmlFor="add-room-name">Room Name</label>
                        <input
                            id="add-room-name"
                            type="text"
                            value={roomName}
                            onChange={(e) => {
                                setRoomName(e.target.value);
                                if (error) setError(null);
                            }}
                            placeholder="e.g. Apartment 301"
                            autoFocus
                            required
                        />
                    </div>

                    {type === 'apartment' && (
                        <div className="form-group">
                            <label htmlFor="add-owner-name">Owner Name</label>
                            <input
                                id="add-owner-name"
                                type="text"
                                value={ownerName}
                                onChange={(e) => setOwnerName(e.target.value)}
                                placeholder="e.g. John Smith"
                            />
                        </div>
                    )}

                    {type === 'common_room' && (
                        <div className="form-group">
                            <label htmlFor="add-common-type">Common Room Type</label>
                            <select
                                id="add-common-type"
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
                        <button type="submit" className="btn btn-primary">Add Room</button>
                    </div>
                </form>
            </div>
        </div>
    );
}

export default AddRoomModal;
