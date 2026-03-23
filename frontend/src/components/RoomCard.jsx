function RoomCard({ room, onEdit, onRemove }) {
    const isHeating = room.heatingEnabled;
    const isCooling = room.coolingEnabled;
    const isIdle = !isHeating && !isCooling;

    const cardClass = `room-card ${isHeating ? 'heating' : ''} ${isCooling ? 'cooling' : ''} ${isIdle ? 'idle' : ''}`.trim();

    const statusLabel = isHeating ? 'Heating' : isCooling ? 'Cooling' : 'Idle';
    const statusClass = isHeating ? 'status-heating' : isCooling ? 'status-cooling' : 'status-idle';

    return (
        <div className={cardClass}>
            <div className="room-card-header">
                <div className="room-name">{room.roomName}</div>
                <span className={`room-type-badge ${room.type === 'apartment' ? 'badge-apartment' : 'badge-common'}`}>
                    {room.type === 'apartment' ? '🏠 Apartment' : '🏛️ Common'}
                </span>
            </div>

            <div className="room-detail">
                ID: <span>{room.id}</span>
            </div>

            {room.type === 'apartment' && room.ownerName && (
                <div className="room-detail">
                    Owner: <span>{room.ownerName}</span>
                </div>
            )}

            {room.type === 'common_room' && room.commonRoomType && (
                <div className="room-detail">
                    Type: <span>{room.commonRoomType}</span>
                </div>
            )}

            <div className="room-detail">
                Deadband: <span>±{room.deadband}°C</span>
            </div>

            <div className="room-temp">
                <span className="room-temp-value">{room.currentTemperature}</span>
                <span className="room-temp-unit">°C</span>
            </div>

            <div className="room-status">
                <span className={`status-tag ${statusClass}`}>
                    <span className="status-dot"></span>
                    {statusLabel}
                </span>
                {isHeating && <span className="status-tag status-heating">🔥 Heater ON</span>}
                {isCooling && <span className="status-tag status-cooling">❄️ AC ON</span>}
                {isIdle && <span className="status-tag status-idle">✅ At Target</span>}
            </div>

            <div className="room-actions">
                <button className="btn btn-secondary" onClick={onEdit}>✏️ Edit</button>
                <button className="btn btn-danger" onClick={onRemove}>🗑️ Remove</button>
            </div>
        </div>
    );
}

export default RoomCard;
