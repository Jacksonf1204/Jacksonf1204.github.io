// Select elements
const sidebar = document.getElementById('sidebar');
const content = document.getElementById('content');
const toggleBtn = document.getElementById('toggle-btn');

// Add event listener to toggle button
toggleBtn.addEventListener('click', () => {
    sidebar.classList.toggle('collapsed'); // Toggle the 'collapsed' class
    content.classList.toggle('expanded'); // Expand content when sidebar is collapsed
});
