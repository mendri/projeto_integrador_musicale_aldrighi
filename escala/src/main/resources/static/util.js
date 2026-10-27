let modalComFocoAtivo = null;
let manipuladorDeTecladoDoModal = null;

function obterElementosFocaveis(modal) {
	return Array.from(modal.querySelectorAll(
		'a[href], button:not([disabled]), input:not([type="hidden"]):not([disabled]), textarea:not([disabled]), select:not([disabled]), [tabindex]:not([tabindex="-1"])'
	)).filter((elemento) => !elemento.closest('[hidden]') && elemento.offsetParent !== null);
}

function ativarTravaDeFoco(modal) {
	if (modalComFocoAtivo && manipuladorDeTecladoDoModal) {
		modalComFocoAtivo.removeEventListener('keydown', manipuladorDeTecladoDoModal);
	}

	modalComFocoAtivo = modal;
	manipuladorDeTecladoDoModal = (evento) => {
		if (evento.key === 'Escape') {
			evento.preventDefault();
			fecharModal(modal.id);
			return;
		}

		if (evento.key !== 'Tab') {
			return;
		}

		const elementosFocaveis = obterElementosFocaveis(modal);
		if (elementosFocaveis.length === 0) {
			evento.preventDefault();
			modal.focus();
			return;
		}

		const primeiroElemento = elementosFocaveis[0];
		const ultimoElemento = elementosFocaveis[elementosFocaveis.length - 1];

		if (evento.shiftKey && document.activeElement === primeiroElemento) {
			evento.preventDefault();
			ultimoElemento.focus();
		} else if (!evento.shiftKey && document.activeElement === ultimoElemento) {
			evento.preventDefault();
			primeiroElemento.focus();
		}
	};

	modal.addEventListener('keydown', manipuladorDeTecladoDoModal);
}

function desativarTravaDeFoco(modal) {
	if (modal === modalComFocoAtivo && manipuladorDeTecladoDoModal) {
		modal.removeEventListener('keydown', manipuladorDeTecladoDoModal);
		modalComFocoAtivo = null;
		manipuladorDeTecladoDoModal = null;
	}
}

function abrirModal(modalId) {
	const modal = document.getElementById(modalId);
	if (modal) {
		modal.classList.remove('hidden');
		modal.classList.remove('opacity-0');
		modal.querySelector('.transform').classList.remove('scale-95');
		modal.querySelector('.transform').classList.add('scale-100');

		if (!modal.hasAttribute('tabindex')) {
			modal.setAttribute('tabindex', '-1');
		}

		const primeiroInput = modal.querySelector(
			'input:not([type="hidden"]):not([disabled]), textarea:not([disabled]), select:not([disabled])'
		);
		ativarTravaDeFoco(modal);
		(primeiroInput || modal).focus();
	}
}

function fecharModal(modalId) {
	const modal = document.getElementById(modalId);
	if (modal) {
		desativarTravaDeFoco(modal);
		modal.classList.add('opacity-0');
		modal.querySelector('.transform').classList.remove('scale-100');
		modal.querySelector('.transform').classList.add('scale-95');
		setTimeout(() => {
			modal.classList.add('hidden');
		}, 50);
	}
}