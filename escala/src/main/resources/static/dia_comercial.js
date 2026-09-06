function iniciarEditarDiaComercial(diaId) {
	document.getElementById('editar-dia-comercial-title').textContent = 'Editar Dia Comercial - ' + diasComerciais[diaId].descricao;
	document.getElementById('editar-dia-comercial-ativo').checked = diasComerciais[diaId].ativo;

	alimentarHorarios(diaId);

	document.getElementById('btn-editar-dia').removeAttribute('onclick');
	document.getElementById('btn-editar-dia').setAttribute('onclick', `salvarDiaComercial(${diaId})`);

	if (diasComerciais[diaId].ativo) {
		document.getElementById('btn-adiciona-horario').removeAttribute('disabled');
	} else {
		document.getElementById('btn-adiciona-horario').setAttribute('disabled', 'true');
	}

	abrirModal('modal-editar-dia');
}

async function salvarDiaComercial(diaId) {
	const ativo = document.getElementById('editar-dia-comercial-ativo').checked;

	let response = await fetch(`/api/dia-comercial/${diaId}`, {
		method: 'PUT',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify({ 'ativo': ativo })
	});

	const { message } = await response.json();

	if (response.ok) {
		diasComerciais[diaId].ativo = ativo;
		alert('Dia comercial atualizado com sucesso!');
		location.reload();
	} else {
		alert(message);
	}
}

function alimentarHorarios(diaId) {
	const ulHorarios = document.getElementById('editar-dia-comercial-horarios');

	ulHorarios.innerHTML = '';

	const horarios = diasHorarios[diaId] || [];

	horarios.forEach(horario => {
		const li = document.createElement('li');
		li.className = 'flex items-center justify-between py-2 border-b border-slate-700/60';
		li.innerHTML = `
			<span class="text-slate-200">${horario.horaInicio} - ${horario.horaFim}</span>
			<button type="button" class="text-red-500 hover:text-red-700 p-1 rounded-lg cursor-pointer" aria-label="Remover" onclick="removerHorario(${horario.id}, ${diaId})">
				<svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
					<path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.8" d="M6 18L18 6M6 6l12 12"></path>
				</svg>
			</button>
		`;
		ulHorarios.appendChild(li);
	})

	document.getElementById('btn-adiciona-horario').removeAttribute('onclick');
	document.getElementById('btn-adiciona-horario').setAttribute('onclick', `adicionarHorario(${diaId})`);
}

async function adicionarHorario(diaId) {
	const horaInicio = prompt('Digite a hora de início (HH:mm):');
	if (!horaInicio) return;
	if (!/^([01]\d|2[0-3]):[0-5]\d$/.test(horaInicio)) {
		alert('Digite um horário válido no formato HH:mm.');
		adicionarHorario(diaId);
		return;
	}
	const horaFim = prompt('Digite a hora de fim (HH:mm):');
	if (!horaFim) return;
	if (!/^([01]\d|2[0-3]):[0-5]\d$/.test(horaFim)) {
		alert('Digite um horário válido no formato HH:mm.');
		adicionarHorario(diaId);
		return;
	}

	const response = await fetch(`/api/horario-comercial/${diaId}`, {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify({ 'horaInicio': horaInicio, 'horaFim': horaFim, "diaComercialId": diaId })
	});

	const { message, horario } = await response.json();

	if (response.ok) {
		if (!diasHorarios[diaId]) {
			diasHorarios[diaId] = [];
		}
		diasHorarios[diaId].push(horario);
		alimentarHorarios(diaId);
	} else {
		alert(message);
	}
}

async function removerHorario(horarioId, diaId) {
	const response = await fetch(`/api/horario-comercial/${horarioId}`, {
		method: 'DELETE'
	});

	const { message } = await response.json();

	if (response.ok) {
		diasHorarios[diaId] = diasHorarios[diaId].filter(horario => horario.id !== horarioId);
		alimentarHorarios(diaId);
	} else {
		alert(message);
	}
}
