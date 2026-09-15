function iniciarEditarEscala(diaId, horarioId) {
	const horario = horariosDisponiveis[diaId].find(horario => horario.id === horarioId);
	alimentarAlunos(horario);

	abrirModal('modal-editar-escala');
}

function alimentarAlunos(horario) {
	const ulAlunos = document.getElementById('editar-escala-alunos');

	ulAlunos.innerHTML = '';

	const escalaFiltrada = escala[horario.diaComercial.id].filter(aluno => aluno.horarioComercial.id === horario.id);

	escalaFiltrada.forEach(ef => {
		const li = document.createElement('li');
		li.className = 'flex items-center justify-between py-2 border-b border-slate-700/60';
		li.innerHTML = `
			<div class="flex flex-col">
				<span class="text-slate-200">${ef.aluno.nome}</span>
				<span class="text-slate-400 text-sm">${ef.aula.descricao}</span>
			</div>
			<button type="button" class="text-red-500 hover:text-red-700 p-1 rounded-lg cursor-pointer" aria-label="Remover" onclick="removerAluno(${ef.id})">
				<svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
					<path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.8" d="M6 18L18 6M6 6l12 12"></path>
				</svg>
			</button>
		`;
		ulAlunos.appendChild(li);
	})

	document.getElementById('btn-adiciona-aluno').removeAttribute('onclick');
	document.getElementById('btn-adiciona-aluno').setAttribute('onclick', `adicionarAluno(${horario.diaComercial.id})`);
}