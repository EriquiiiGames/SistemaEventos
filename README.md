+--------------------+ 
| Usuario | 
+--------------------+ 
| - nome: String | 
| - email: String | 
| - idade: int | 
| - eventosConfirmados: List<Evento> 
| +--------------------+ 
| + confirmarPresenca(e: Evento): void |
| + cancelarPresenca(e: Evento): void | 
| + listarEventosConfirmados(): void | 
+--------------------+

+--------------------+ 
| Evento |
+--------------------+
| - nome: String | 
| - endereco: String | 
| - categoria: String| 
| - horario: LocalDateTime | 
| - descricao: String| 
+--------------------+ 
| + toString(): String | 
+--------------------+ 

+---------------------------+ 
| Main / SistemaEventos | 
+---------------------------+ 
| - eventos: List<Evento> | 
| - usuario: Usuario | 
| - FILE_NAME: String | 
+---------------------------+ 
| + main(args: String[]): void |
| + cadastrarEvento(sc: Scanner): void |
| + listarEventos(): void | 
| + confirmarPresenca(sc: Scanner): void |
| + cancelarPresenca(sc: Scanner): void |
| + listarEventosConfirmados(): void |
| + listarEventosAtuais(): void |
| + listarEventosPassados(): void | 
| + salvarEventos(): void | 
| + carregarEventos(): void |
+---------------------------+
