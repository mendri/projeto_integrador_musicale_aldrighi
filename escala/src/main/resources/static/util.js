function abrirModal(modalId) {
	const modal = document.getElementById(modalId);
	if (modal) {
		modal.classList.remove('hidden');
		modal.classList.remove('opacity-0');
		modal.querySelector('.transform').classList.remove('scale-95');
		modal.querySelector('.transform').classList.add('scale-100');
	}
}

function fecharModal(modalId) {
	const modal = document.getElementById(modalId);
	if (modal) {
		modal.classList.add('opacity-0');
		modal.querySelector('.transform').classList.remove('scale-100');
		modal.querySelector('.transform').classList.add('scale-95');
		setTimeout(() => {
			modal.classList.add('hidden');
		}, 50);
	}
}