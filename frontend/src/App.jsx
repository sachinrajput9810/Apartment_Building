import { useState, useEffect, useCallback } from 'react';
import BuildingHeader from './components/BuildingHeader';
import RoomCard from './components/RoomCard';
import AddRoomModal from './components/AddRoomModal';
import EditRoomModal from './components/EditRoomModal';
import { getBuilding, setRequestedTemperature, addApartment, addCommonRoom, updateRoom, removeRoom } from './api';

function App() {
    const [building, setBuilding] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [addModalType, setAddModalType] = useState(null); // 'apartment' | 'common_room' | null
    const [editingRoom, setEditingRoom] = useState(null);

    const fetchBuilding = useCallback(async () => {
        try {
            const data = await getBuilding();
            setBuilding(data);
            setError(null);
        } catch (err) {
            setError(err.message);
        } finally {
            setLoading(false);
        }
    }, []);

    // Initial load
    useEffect(() => {
        fetchBuilding();
    }, [fetchBuilding]);

    // Auto-refresh every 3 seconds to see temperature simulation
    useEffect(() => {
        const interval = setInterval(fetchBuilding, 3000);
        return () => clearInterval(interval);
    }, [fetchBuilding]);

    const handleSetTemperature = async (temp) => {
        try {
            const data = await setRequestedTemperature(temp);
            setBuilding(data);
        } catch (err) {
            setError(err.message);
        }
    };

    const handleAddRoom = async (type, formData) => {
        try {
            if (type === 'apartment') {
                await addApartment(formData.roomName, formData.ownerName);
            } else {
                await addCommonRoom(formData.roomName, formData.commonRoomType);
            }
            setAddModalType(null);
            await fetchBuilding();
        } catch (err) {
            setError(err.message);
        }
    };

    const handleEditRoom = async (roomId, data) => {
        try {
            await updateRoom(roomId, data);
            setEditingRoom(null);
            await fetchBuilding();
        } catch (err) {
            setError(err.message);
        }
    };

    const handleRemoveRoom = async (roomId) => {
        if (!window.confirm('Are you sure you want to remove this room?')) return;
        try {
            await removeRoom(roomId);
            await fetchBuilding();
        } catch (err) {
            setError(err.message);
        }
    };

    if (loading) {
        return (
            <div className="loading">
                <div className="spinner"></div>
                <p>Connecting to building controls...</p>
            </div>
        );
    }

    if (error && !building) {
        return (
            <div className="error">
                <h2>⚠️ Connection Error</h2>
                <p>{error}</p>
                <button className="btn btn-primary" onClick={fetchBuilding} style={{ marginTop: 16 }}>
                    Retry
                </button>
            </div>
        );
    }

    return (
        <div className="app">
            <header className="app-header">
                <h1>🏢 Building Controls</h1>
                <p>Real-time temperature monitoring and HVAC management</p>
            </header>

            {building && (
                <>
                    <BuildingHeader
                        building={building}
                        onSetTemperature={handleSetTemperature}
                    />

                    <div className="rooms-header">
                        <h2>Rooms ({building.rooms.length})</h2>
                        <div className="add-buttons">
                            <button className="btn btn-primary" onClick={() => setAddModalType('apartment')}>
                                + Apartment
                            </button>
                            <button className="btn btn-success" onClick={() => setAddModalType('common_room')}>
                                + Common Room
                            </button>
                        </div>
                    </div>

                    <div className="rooms-grid">
                        {building.rooms.map((room) => (
                            <RoomCard
                                key={room.id}
                                room={room}
                                onEdit={() => setEditingRoom(room)}
                                onRemove={() => handleRemoveRoom(room.id)}
                            />
                        ))}
                    </div>

                    {building.rooms.length === 0 && (
                        <div style={{ textAlign: 'center', padding: 40, color: 'var(--text-muted)' }}>
                            <p>No rooms yet. Add an apartment or common room to get started.</p>
                        </div>
                    )}
                </>
            )}

            {addModalType && (
                <AddRoomModal
                    type={addModalType}
                    onAdd={handleAddRoom}
                    onClose={() => setAddModalType(null)}
                />
            )}

            {editingRoom && (
                <EditRoomModal
                    room={editingRoom}
                    onSave={handleEditRoom}
                    onClose={() => setEditingRoom(null)}
                />
            )}
        </div>
    );
}

export default App;
