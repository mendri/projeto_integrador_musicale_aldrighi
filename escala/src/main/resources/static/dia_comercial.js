function iniciarEditarDiaComercial(diaId) {
	document.getElementById('editar-dia-comercial-title').textContent = 'Editar Dia Comercial - ' + diasComerciais[diaId].descricao;
	document.getElementById('editar-dia-comercial-ativo').checked = diasComerciais[diaId].ativo;

	alimentarHorarios(diaId);
	abrirModal('modal-editar-dia');
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
			<button type="button" class="text-red-500 hover:text-red-700 p-1 rounded-lg cursor-pointer" aria-label="Remover" onclick="removerHorario(${horario.id})">
				<svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
					<path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.8" d="M6 18L18 6M6 6l12 12"></path>
				</svg>
			</button>
		`;
		ulHorarios.appendChild(li);
	})
}