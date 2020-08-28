create table  T_KSXX(
					KSBH  char(6)  primary key,
					KSMC   char(10)  not null,
					PYZS    char(8)  not null
					);
create  index index1 on T_KSXX( KSBH);
create  table T_BRXX(
					BRBH char(6) primary key,
					BRMC  char(10)  not  null,
					DLKL  char(8)   not null,
					YCJE  decimal(10,2)  not null,

					DLRQ  datetime
					);
create index index1 on T_BRXX(BRBH);
create  table T_KSYS(
					YSBH  char(6)  primary key,
					KSBH  char(6) not null,
					YSMC  char(10) not null,
					PYZS  char(4)  not null,
					DLKL  char(8) not null,
					SFZJ  bit   not null,
					DLRQ  datetime
					foreign key(KSBH) references T_KSXX(KSBH)
					);
create index index1 on T_KSYS(YSBH);
create index index2 on T_KSYS(KSBH);
create table  T_HZXX(
					HZBH char(6) primary key,
					HZMC char(12) not null,
					PYZS char(4)  not null,
					KSBH char(6)  not null,
					SFZJ bit  not null,
					GHRS int  not null,
					GHFY decimal(8,2)  not null,
					foreign key (KSBH) references T_KSXX(KSBH)
					);
create index index1 on T_HZXX(HZBH);
create index  index2 on T_HZXX(KSBH);
create table T_GHXX(
				    GHBH char(6) primary key,
					HZBH char(6) not null,
					YSBH char(6) not null,
					BRBH char(6) not null,
					GHRC int     not null,
					THBZ bit     not null,
					GHFY  decimal(8,2) not null,
					RQSJ  datetime not null,
					foreign key (HZBH) references T_HZXX(HZBH),
					foreign key (YSBH) references T_KSYS(YSBH),
					foreign key (BRBH) references T_BRXX(BRBH)
					);
create index index1 on T_GHXX(GHBH);
create index index2 on T_GHXX(HZBH);
create index index3 on T_GHXX(YSBH);
create index index4 on T_GHXX(BRBH);
create index index5 on T_GHXX(GHRC);
