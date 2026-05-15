// File: public/js/main.js
document.addEventListener('DOMContentLoaded', function() {
    console.log('Travel Itinerary App initialized');
    
    // Initialize tooltips
    initializeTooltips();
    
    // Initialize modals
    initializeModals();
    
    // Initialize forms
    initializeForms();
});
 
function initializeTooltips() {
    const tooltips = document.querySelectorAll('[data-tooltip]');
    tooltips.forEach(tooltip => {
        tooltip.addEventListener('mouseenter', function() {
            const message = this.getAttribute('data-tooltip');
            console.log('Tooltip:', message);
        });
    });
}
 
function initializeModals() {
    const closeButtons = document.querySelectorAll('.modal-close');
    closeButtons.forEach(btn => {
        btn.addEventListener('click', function() {
            this.closest('.modal').style.display = 'none';
        });
    });
 
    window.addEventListener('click', function(event) {
        const modals = document.querySelectorAll('.modal');
        modals.forEach(modal => {
            if (event.target === modal) {
                modal.style.display = 'none';
            }
        });
    });
}
 
function initializeForms() {
    const forms = document.querySelectorAll('form');
    forms.forEach(form => {
        form.addEventListener('submit', function(e) {
            if (!validateForm(this)) {
                e.preventDefault();
                alert('Please fill in all required fields');
            }
        });
    });
}
 
function validateForm(form) {
    const inputs = form.querySelectorAll('input[required], textarea[required], select[required]');
    for (let input of inputs) {
        if (!input.value.trim()) {
            input.classList.add('is-invalid');
            return false;
        }
    }
    return true;
}
 
function openModal(modalId) {
    document.getElementById(modalId).style.display = 'block';
}
 
function closeModal(modalId) {
    document.getElementById(modalId).style.display = 'none';
}