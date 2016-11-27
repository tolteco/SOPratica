#include <stdio.h>
#include <stdlib.h>

typedef struct fat_extBS_16
{
	//extended fat12 and fat16 stuff
	unsigned char			bios_drive_num;
	unsigned char			reserved1;
	unsigned char			boot_signature;
	unsigned int			volume_id;
	unsigned char			volume_label[11];
	unsigned char			fat_type_label[8];
 
}__attribute__((packed)) fat_extBS_16_t;
 
typedef struct fat_BS
{
	unsigned char 		bootjmp[3];
	unsigned char 		oem_name[8];
	unsigned short		bytes_per_sector;
	unsigned char			sectors_per_cluster;
	unsigned short		reserved_sector_count;
	unsigned char			table_count;
	unsigned short		root_entry_count;
	unsigned short		total_sectors_16;
	unsigned char			media_type;
	unsigned short		table_size_16;
	unsigned short		sectors_per_track;
	unsigned short		head_side_count;
	unsigned int 			hidden_sector_count;
	unsigned int 			total_sectors_32;
 
	//this will be cast to it's specific type once the driver actually knows what type of FAT this is.
	unsigned char		extended_section[54];
 
}__attribute__((packed)) fat_BS_t;

typedef struct arquivos
{
	unsigned char 		nome[11];
	unsigned char			atributo;
	unsigned char 		migue[14];
	unsigned short		fcluster;
	unsigned int 			tam;
}__attribute__((packed)) Arq;

FILE *f;

int main(){
	fat_BS_t boot;
	Arq q;
	int A = 0;
	int F = 0;
	int count = 0;
	int i = 0;
	unsigned char B = 1;
	unsigned char P[31];
	unsigned char n[13];

	f = fopen("floppy2.img", "rw");
	fread(&boot, 1, sizeof(boot), f);
	printf("Reservados ao Boot Record: %hu\n", boot.reserved_sector_count);
	printf("Quantidade de FAT's: %hu\n", boot.table_count);
	printf("Tamanho da FAT: %hu\n", boot.table_size_16);
	printf("Bytes por setor: %hu\n", boot.bytes_per_sector);
	A = (boot.reserved_sector_count + (boot.table_count * boot.table_size_16)) * 	boot.bytes_per_sector;
	//printf("%i\n", A);

	fseek(f, A, SEEK_SET);
	while(F == 0){
		fread(&q, 1, sizeof(q), f);
		//puts(q.nome);
		//printf("atributo %hu\n", q.atributo);
		if (q.nome[0] != 0 && q.atributo != 15 && q.nome[0] != 229){
			strncpy(n, q.nome, 11);
			n[11] = '\0';
			//printf("in if\n");
			printf("Nome: %s", n);
			if (q.atributo == 32){
				printf("; First cluster: %hu; Tamanho: %i bytes", q.fcluster, q.tam);
			}
			printf("\n");
		}
		if (q.nome[0] == 0){
			F = 1;
		}
	}
	return 0;
}
