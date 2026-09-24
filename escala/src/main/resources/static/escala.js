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
			<button type="button" class="text-red-500 hover:text-red-700 p-1 rounded-lg cursor-pointer" aria-label="Remover" onclick="removerAluno(${ef.id}, ${horario.id}, ${horario.diaComercial.id})">
				<svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
					<path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.8" d="M6 18L18 6M6 6l12 12"></path>
				</svg>
			</button>
		`;
		ulAlunos.appendChild(li);
	})

	document.getElementById('btn-iniciar-adicionar-aluno').removeAttribute('onclick');
	document.getElementById('btn-iniciar-adicionar-aluno').setAttribute('onclick', `iniciarAdicionarAluno(${horario.id}, ${horario.diaComercial.id})`);
}

function iniciarAdicionarAluno(horarioComercialId, diaComercialId) {
	document.getElementById('btn-adicionar-aluno').removeAttribute('onclick');
	document.getElementById('btn-adicionar-aluno').setAttribute('onclick', `adicionarAluno(${horarioComercialId}, ${diaComercialId})`);

	abrirModal('modal-adicionar-aluno');
}

async function adicionarAluno(horarioComercialId, diaComercialId) {
	const horario = horariosDisponiveis[diaComercialId].find(horario => horario.id === horarioComercialId);
	let alunoId = document.getElementById('aluno-select').value;
	let aulaId = document.getElementById('aula-select').value;

	if (!alunoId || !aulaId) {
		alert('Selecione um aluno e uma aula');
		return;
	}

	let escalaMap = {}
	escalaMap["horarioComercial"] = { ...horario };
	escalaMap["aluno"] = { ...alunos.find(a => a.id == parseInt(alunoId)) };
	escalaMap["aula"] = { ...aulas.find(a => a.id == parseInt(aulaId)) };

	const response = await fetch('/api/escala', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(escalaMap)
	});

	if (!response.ok) {
		throw new Error('Erro ao adicionar aluno');
	}

	const escalaResponse = await response.json();

	escala[diaComercialId] = [...escala[diaComercialId], escalaResponse];

	alimentarAlunos(horario);
	fecharModal('modal-adicionar-aluno');
}

async function removerAluno(escalaId, horarioComercialId, diaComercialId) {
	const horario = horariosDisponiveis[diaComercialId].find(horario => horario.id === horarioComercialId);

	const response = await fetch(`/api/escala/${escalaId}`, {
		method: 'DELETE',
		headers: {
			'Content-Type': 'application/json'
		}
	});

	if (!response.ok) {
		throw new Error('Erro ao remover aluno');
	}

	escala[diaComercialId] = escala[diaComercialId].filter(e => e.id != escalaId);

	alimentarAlunos(horario);
}