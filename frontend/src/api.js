const API_BASE = '/api';

export async function getBuilding() {
    const res = await fetch(`${API_BASE}/building`);
    if (!res.ok) throw new Error('Failed to fetch building');
    return res.json();
}

export async function setRequestedTemperature(requestedTemperature) {
    const res = await fetch(`${API_BASE}/building/temperature`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ requestedTemperature }),
    });
    if (!res.ok) throw new Error('Failed to set temperature');
    return res.json();
}

export async function addApartment(roomName, ownerName) {
    const res = await fetch(`${API_BASE}/rooms/apartment`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ roomName, ownerName }),
    });
    if (!res.ok) {
        const msg = await res.text();
        throw new Error(msg || 'Failed to add apartment');
    }
    return res.json();
}

export async function addCommonRoom(roomName, commonRoomType) {
    const res = await fetch(`${API_BASE}/rooms/common-room`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ roomName, commonRoomType }),
    });
    if (!res.ok) {
        const msg = await res.text();
        throw new Error(msg || 'Failed to add common room');
    }
    return res.json();
}

export async function updateRoom(roomId, data) {
    const res = await fetch(`${API_BASE}/rooms/${roomId}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data),
    });
    if (!res.ok) throw new Error('Failed to update room');
    return res.json();
}

export async function removeRoom(roomId) {
    const res = await fetch(`${API_BASE}/rooms/${roomId}`, {
        method: 'DELETE',
    });
    if (!res.ok) throw new Error('Failed to remove room');
}
