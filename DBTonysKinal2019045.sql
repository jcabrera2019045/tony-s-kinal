drop database if exists DBTonysKinal2020;
create database DBTonysKinal2019045;
use DBTonysKinal2019045;
#ALTER USER 'root'@'localhost' identified WITH mysql_native_password BY 'admin';

#--------------------Crear--------------------#

#--------------------Tipo Empleado--------------------#
delimiter $$
create procedure crear_Templeado()
begin
	create table Templeado(
    codigoTempleado int not null primary key auto_increment,
    descripcion varchar(100));
end $$
delimiter ;

#--------------------Tipo Plato--------------------#
delimiter $$
create procedure crear_Tplato()
begin
	create table Tplato(
    codigoTplato int not null primary key auto_increment,
    descripcion varchar(100));
end $$
delimiter ;

#--------------------Empresas--------------------#
delimiter $$
create procedure crear_Empresas()
begin
	create table Empresas(
    codigoEmpresa int not null primary key auto_increment,
    nombreEmpresa varchar(150),
    direccion varchar(150),
    telefono varchar(10));
end $$
delimiter ;

#--------------------Empleados--------------------#
delimiter $$
create procedure crear_Empleados()
begin
	create table Empleados(
	foreign key (codigoTempleado) references Templeado(CodigoTempleado) on delete cascade,
    codigoEmpleado int not null primary key auto_increment,
    apellidosEmpleado varchar(50),
    nombreEmpleado varchar(50),
    direccionEmpleado varchar(50),
    telefonoContacto varchar(10),
    gradoCocinero varchar(50),
    codigoTempleado int);
end $$
delimiter ;

#--------------------Servicios--------------------#
delimiter $$
create procedure crear_Servicios()
begin
	create table Servicios(
    foreign key (codigoEmpresa) references Empresas(codigoEmpresa) on delete cascade,
    codigoServicio int not null primary key auto_increment,
    fechaServicio Date,
    tipoServicio varchar(100),
    horaServicio time,
    lugarServicio varchar(50),
    telefonoContacto varchar(10),
    codigoEmpresa int);
end $$
delimiter ;

#--------------------Presupuesto--------------------#
delimiter $$
create procedure crear_Presupuesto()
begin
	create table Presupuesto(
    foreign key (codigoEmpresa) references Empresas(codigoEmpresa) on delete cascade,
    codigoPresupuesto int not null primary key auto_increment,
    fechaSolicitud date,
    cantidadPresupuesto decimal,
    codigoEmpresa int);
end $$
delimiter ;

#--------------------Servicios Empleados--------------------#
delimiter $$
create procedure crear_Sempleados()
begin
	create table Sempleados(
    foreign key (codigoServicio) references Servicios(codigoServicio) on delete cascade,
    foreign key (codigoEmpleado) references Empleados(codigoEmpleado) on delete cascade,
    codigoSempleados int not null primary key auto_increment,
    fechaEvento date,
    horaEvento time,
    lugarEvento varchar(50),
    codigoServicio int not null,
    codigoEmpleado int not null);
end $$
delimiter ;

#--------------------Productos--------------------#
delimiter $$
create procedure crear_Productos()
begin
	create table Productos(
    codigoProducto int not null primary key auto_increment,
    nombreProducto varchar(50),
    cantidad int);
end $$
delimiter ;

#--------------------Platos--------------------#
delimiter $$
create procedure crear_Platos()
begin
	create table Platos(
    foreign key (codigoTplato) references Tplato(codigoTplato) on delete cascade,
    codigoPlato int not null primary key auto_increment,
    cantidad int,
    nombrePlato varchar(50),
    descripcionPlato varchar(100),
    precioPlato decimal,
    codigoTplato int);
end $$
delimiter ;

#--------------------Productos Platos--------------------#
delimiter $$
create procedure crear_Pplatos()
begin
	create table Pplatos(
    foreign key (codigoProducto) references Productos(codigoProducto) on delete cascade,
    foreign key (codigoPlato) references Platos(codigoPlato)on delete cascade,
    codigoProducto int not null,
    codigoPlato int not null);
end $$
delimiter ;

#--------------------Servicios Platos--------------------#
delimiter $$
create procedure crear_Splatos()
begin
	create table Splatos(
    foreign key (codigoServicio) references Servicios(codigoServicio)on delete cascade,
    foreign key (codigoPlato) references Platos(codigoPlato)on delete cascade,
    codigoServicio int not null,
    codigoPlato int not null);
end $$
delimiter ;

#--------------------ABC--------------------#

#--------------------Templeado--------------------#

delimiter $$
create procedure sp_ListarTempleado()
begin
	select Te.codigoTempleado, Te.descripcion from Templeado Te;
end $$
delimiter ;

delimiter $$
create procedure sp_AgregarTempleado(in descripcionP varchar(100))
begin
	insert into Templeado(descripcion) values (descripcionP);
end $$
delimiter ;

delimiter $$
create procedure sp_ActualizarTempleado(in varP int, in descripcionP varchar(100))
begin
	update Templeado set descripcion=descripcionP where codigoTempleado=varP;
end $$
delimiter ;

delimiter $$
create procedure sp_EliminarTempleado(in varP int)
begin
	delete from Templeado  where codigoTempleado=varP;
end $$
delimiter ;

delimiter $$
create procedure sp_BuscarTempleado(in varP int)
begin
	select Te.codigoTempleado, Te.descripcion from Templeado Te where Te.codigoTempleado=varP;
end $$
delimiter ;

#--------------------Tplato--------------------#

delimiter $$
create procedure sp_ListarTplato()
begin
	select Tp.codigoTplato, Tp.descripcion from Tplato Tp;
end $$
delimiter ;

delimiter $$
create procedure sp_AgregarTplato(in descripcionP varchar(100))
begin
	insert into Tplato(descripcion) values (descripcionP);
end $$
delimiter ;

delimiter $$
create procedure sp_ActualizarTplato(in varP int, in descripcionP varchar(100))
begin
	update Tplato set descripcion=descripcionP where codigoTplato=varP;
end $$
delimiter ;

delimiter $$
create procedure sp_EliminarTplato(in varP int)
begin
	delete from Tplato where codigoTplato=varP;
end $$
delimiter ;

delimiter $$
create procedure sp_BuscarTplato(in varP int)
begin
	select Tp.codigoTplato, Tp.descripcion from Tplato Tp where Tp.codigoTplato=varP;
end $$
delimiter ;

#--------------------Empresas--------------------#

delimiter $$
create procedure sp_ListarEmpresas()
begin
	select Epr.codigoEmpresa, Epr.nombreEmpresa, Epr.direccion, Epr.telefono from Empresas Epr;
end $$
delimiter ;

delimiter $$
create procedure sp_AgregarEmpresa(in nombreP varchar(100), in direccionP varchar(100), in telefonoP varchar(10))
begin
	insert into Empresas(nombreEmpresa, direccion, telefono) values (nombreP, direccionP, telefonoP);
end $$
delimiter ;

delimiter $$
create procedure sp_ActualizarEmpresa(in varP int, in nombreP varchar(100), in direccionP varchar(100), in telefonoP varchar(10))
begin
	update Empresas set nombreEmpresa=nombreP, direccion=direccionP, telefono=telefonoP where codigoEmpresa=varP;
end $$
delimiter ;

delimiter $$
create procedure sp_EliminarEmpresa(in varP int)
begin
	delete from Empresas where codigoEmpresa=varP;
end $$
delimiter ;

delimiter $$
create procedure sp_BuscarEmpresa(in varP int)
begin
	select Epr.codigoEmpresa, Epr.nombreEmpresa, Epr.direccion, Epr.telefono from Empresas Epr where Epr.codigoEmpresa=varP;
end $$
delimiter ;

#--------------------Empleados--------------------#

delimiter $$
create procedure sp_ListarEmpleados()
begin
	select Epl.codigoEmpleado, Epl.apellidosEmpleado, Epl.nombreEmpleado, Epl.direccionEmpleado, Epl.telefonoContacto, Epl.gradoCocinero, Epl.codigoTempleado from Empleados Epl;
end $$
delimiter ;

delimiter $$
create procedure sp_AgregarEmpleado(in apellidosP varchar(100), in nombreP varchar(100), in direccionP varchar(100), in telefonoP varchar(10), in gradoP varchar(50), in templeadoP int)
begin
	insert into Empleados(apellidosEmpleado, nombreEmpleado, direccionEmpleado, telefonoContacto, gradoCocinero, codigoTempleado) values (apellidosP, nombreP, direccionP, telefonoP, gradoP, templeadoP);
end $$
delimiter ;

delimiter $$
create procedure sp_ActualizarEmpleado(in varP int, in apellidosP varchar(100), in nombreP varchar(100), in direccionP varchar(100), in telefonoP varchar(10), in gradoP varchar(50), in templeadoP int)
begin
	update Empleados set apellidosEmpleado=apellidosP, nombreEmpleado=nombreP, direccionEmpleado=direccionP, telefonoContacto=telefonoP, gradoCocinero=gradoP, codigoTempleado=templeadoP  where codigoEmpleado=varP;
end $$
delimiter ;

delimiter $$
create procedure sp_EliminarEmpleado(in varP int)
begin
	delete from Empleados where codigoEmpleado=varP;
end $$
delimiter ;

delimiter $$
create procedure sp_BuscarEmpleado(in varP int)
begin
	select Epl.codigoEmpleado, Epl.apellidosEmpleado, Epl.nombreEmpleado, Epl.direccionEmpleado, Epl.telefonoContacto, Epl.gradoCocinero, Epl.codigoTempleado  from Empleados Epl where Epl.codigoEmpleado=varP;
end $$
delimiter ;

#--------------------Servicios--------------------#

delimiter $$
create procedure sp_ListarServicios()
begin
	select S.codigoServicio, S.fechaServicio, S.tipoServicio, S.horaServicio, S.lugarServicio, S.telefonoContacto, S.codigoEmpresa from Servicios S;
end $$
delimiter ;

delimiter $$
create procedure sp_AgregarServicios(in fechaP date, in tipoP varchar(100), in horaP time, in lugarP varchar(100), in telefonoP varchar(10), in empresaP int)
begin
	insert into Servicios(fechaServicio, tipoServicio, horaServicio, lugarServicio, telefonoContacto, codigoEmpresa) values (fechaP, tipoP, horaP, lugarP, telefonoP, empresaP);
end $$
delimiter ;

delimiter $$
create procedure sp_ActualizarServicios(in fechaP date, in tipoP varchar(100), in horaP time, in lugarP varchar(100), in telefonoP varchar(10), in empresaP int, in varP int)
begin
	update Servicios set fechaServicio=fechaP, tipoServicio=tipoP, horaServicio=horaP, lugarServicio=lugarP, telefonoContacto=telefonoP, codigoEmpresa=empresaP where codigoServicio=varP;
end $$
delimiter ;

delimiter $$
create procedure sp_EliminarServicio(in varP int)
begin
	delete from Servicios where codigoServicio=varP;
end $$
delimiter ;

delimiter $$
create procedure sp_BuscarServicio(in varP int)
begin
	select S.codigoServicio, S.fechaServicio, S.tipoServicio, S.horaServicio, S.lugarServicio, S.telefonoContacto, S.codigoEmpresa from Servicios S where S.codigoServicio=varP;
end $$
delimiter ;

#--------------------Presupuesto--------------------#

delimiter $$
create procedure sp_ListarPresupuesto()
begin
	select Pr.codigoPresupuesto, Pr.fechaSolicitud, Pr.cantidadPresupuesto, Pr.codigoEmpresa from Presupuesto Pr;
end $$
delimiter ;

delimiter $$
create procedure sp_AgregarPresupuesto(in fechaP date, in cantidadP decimal, in empresaP int)
begin
	insert into Presupuesto(fechaSolicitud, cantidadPresupuesto, codigoEmpresa) values (fechaP, cantidadP, empresaP);
end $$
delimiter ;

delimiter $$
create procedure sp_ActualizarPresupuesto(in fechaP date, in cantidadP decimal, in empresaP int, in varP int)
begin
	update Presupuesto set fechaSolicitud=fechaP, cantidadPresupuesto=cantidadP, codigoEmpresa=empresaP where codigoPresupuesto=varP;
end $$
delimiter ;

delimiter $$
create procedure sp_EliminarPresupuesto(in varP int)
begin
	delete from Presupuesto where codigoPresupuesto=varP;
end $$
delimiter ;

delimiter $$
create procedure sp_BuscarPresupuesto(in varP int)
begin
	select Pr.codigoPresupuesto, Pr.fechaSolicitud, Pr.cantidadPresupuesto, Pr.codigoEmpresa from Presupuesto Pr where Pr.codigoPresupuesto=varP;
end $$
delimiter ;

#--------------------Servicios Empleado--------------------#

delimiter $$
create procedure sp_ListarSempleado()
begin
	select Se.codigoSempleados, Se.fechaEvento, Se.horaEvento, Se.lugarEvento, Se.codigoServicio, Se.codigoEmpleado from Sempleados Se;
end $$
delimiter ;

delimiter $$
create procedure sp_AgregarSempleado(in fechaP date, in horaP time, in lugarP varchar(150), in servicioP int, in empleadoP int)
begin
	insert into Sempleados(fechaEvento, horaEvento, lugarEvento, codigoServicio, codigoEmpleado) values (fechaP, horaP, lugarP, servicioP, empleadoP);
end $$
delimiter ;

delimiter $$
create procedure sp_ActualizarSempleado(in fechaP date, in horaP time, in lugarP varchar(150), in varP int)
begin
	update Sempleados set fechaEvento = fechaP, horaEvento = horaP, lugarEvento = lugarP where codigoSempleados= varP;
end $$    
delimiter ;    

delimiter $$
create procedure sp_EliminarSempleado(in var int)
begin
	delete from Sempleados where codigoSempleados=var;
end $$
delimiter ;
    
delimiter $$
create procedure sp_BuscarSempleado(in varP int)
begin
	select Se.fechaEvento, Se.horaEvento, Se.lugarEvento, Se.codigoServicio, Se.codigoEmpleado from Sempleados Se where Se.codigoSempleados=varP;
end $$
delimiter ;

#--------------------Productos--------------------#

delimiter $$
create procedure sp_ListarProductos()
begin
	select Pd.codigoProducto, Pd.nombreProducto, Pd.cantidad from Productos Pd;
end $$
delimiter ;

delimiter $$
create procedure sp_AgregarProductos(in nombreP varchar(150), in cantidadP int)
begin
	insert into Productos(nombreProducto, cantidad) values (nombreP, cantidadP);
end $$
delimiter ;

delimiter $$
create procedure sp_ActualizarProductos(in varP int, in nombreP varchar(150), in cantidadP int)
begin
	update Productos set nombreProducto=nombreP, cantidad=cantidadP where codigoProducto=varP;
end $$
delimiter ;

delimiter $$
create procedure sp_EliminarProducto(in varP int)
begin
	delete from Productos where codigoProducto=varP;
end $$
delimiter ;

delimiter $$
create procedure sp_BuscarProducto(in varP int)
begin
	select Pd.codigoProducto, Pd.nombreProducto, Pd.cantidad from Productos Pd where Pd.codigoProducto=varP;
end $$
delimiter ;

#--------------------Platos--------------------#

delimiter $$
create procedure sp_ListarPlatos()
begin
	select Pl.codigoPlato, Pl.cantidad, Pl.nombrePlato, Pl.descripcionPlato, Pl.precioPlato, Pl.codigoTplato from Platos Pl;
end $$
delimiter ;

delimiter $$
create procedure sp_AgregarPlatos(in cantidadP int, in nombreP varchar(50), in descripcionP varchar(150), in precioP decimal, in codigoP int)
begin
	insert into Platos(cantidad, nombrePlato, descripcionPlato, precioPlato, codigoTplato) values (cantidadP, nombreP, descripcionP, precioP, codigoP);
end $$
delimiter ;

delimiter $$
create procedure sp_ActualizarPlatos(in cantidadP int, in nombreP varchar(50), in descripcionP varchar(150), in precioP decimal, in codigoP int, in varP int)
begin
	update Platos  set cantidad=cantidadP, nombrePlato=nombreP, descripcionPlato=descripcionP, precioPlato=precioP, codigoTplato=codigoP where codigoPlato=varP;
end $$
delimiter ;

delimiter $$
create procedure sp_EliminarPlatos(in varP int)
begin
	delete from Platos where codigoPlato=varP;
end $$
delimiter ;

delimiter $$
create procedure sp_BuscarPlatos(in varP int)
begin
	select Pl.codigoPlato, Pl.cantidad, Pl.nombrePlato, Pl.descripcionPlato, Pl.precioPlato, Pl.codigoTplato from Platos Pl where Pl.codigoPlato=varP;
end $$
delimiter ;

#--------------------Productos Platos--------------------#

delimiter $$
create procedure sp_ListarPplatos()
begin
	select Pp.codigoProducto, Pp.codigoPlato from Pplatos Pp;
end $$
delimiter ;

#--------------------Servicios Platos--------------------#

delimiter $$
create procedure sp_ListarSplatos()
begin
	select Sp.codigoServicio, Sp.codigoPlato from Splatos Sp;
end $$
delimiter ;

#--------------------Calls--------------------#

#--------------------Crear--------------------#

call crear_Templeado();
call crear_Tplato();
call crear_Empresas();
call crear_Empleados();
call crear_Servicios();
call crear_Presupuesto();
call crear_Sempleados();
call crear_Productos();
call crear_Platos();
call crear_Pplatos();
call crear_Splatos();

#--------------------Listar--------------------#

call sp_ListarTempleado();
call sp_ListarTplato();
call sp_ListarEmpresas();
call sp_ListarEmpleados();
call sp_ListarServicios();
call sp_ListarPresupuesto();
call sp_ListarSempleado();
call sp_ListarProductos();
call sp_ListarPlatos();
call sp_ListarPplatos();
call sp_ListarSplatos();

#--------------------Agregar--------------------#

call sp_AgregarTempleado("Cocinero");
call sp_AgregarTempleado("Camarero");

call sp_AgregarTplato("Platos Orientales");
call sp_AgregarTplato("Comida Italiana");
call sp_AgregarTplato("Comida Americana");
call sp_AgregarTplato("Platos Tradicionales");
call sp_AgregarTplato("Comida Vegetariana");

call sp_AgregarEmpresa("Coca Cola", "15 av, 7-14 Zona 9 Guatemala", "2940-5820");
call sp_AgregarEmpresa("Mitsubishi", "56 av, 8-45 Zona 19 Guatemala", "8374-0954");
call sp_AgregarEmpresa("Dell", "34 av, 9-21 Zona 5 Guatemala", "0957-6487");
call sp_AgregarEmpresa("Cerveza Gallo", "3 av, 10-87 Zona 14 Guatemala", "3940-1739");
call sp_AgregarEmpresa("ENCA", "14 av, 6-10 Zona 1 Guatemala", "4895-7294");

call sp_AgregarEmpleado("Bolivar Aguirre", "Oscar Estuardo", "12 av., 6-43 Zona 21 Guatemala", "3859-3295", "Chef", 1);
call sp_AgregarEmpleado("Cabrera Estrada", "Emmanuel Antonio", "23 av., 7-25 Zona 14 Guatemala", "4850-8573", null , 2);
call sp_AgregarEmpleado("Salguero Sazo", "Carmen Yulissa", "21 av., 11-76 Zona 7 Guatemala", "3810-2093", "Chef", 1);
call sp_AgregarEmpleado("Mansilla Soto", "Angel Federico", "87 av., 4-83 Zona 23 Guatemala", "9594-0474", "Ayudante de Cocina", 1);
call sp_AgregarEmpleado("Mayorga Guzmán", "Celeste", "56 av., 4-30 Zona 3 Guatemala", "3920-2011", "Repostera", 1);
call sp_AgregarEmpleado("Hurtarte Solorsano", "Jimmy Antonio", "82 av., 7-18 Zona 18 Guatemala", "4820-2651", null, 2);

call sp_AgregarServicios("12-5-19", "Lunch", "19:00", "14 Av. 6-87 Zona 6 Guatemala", "9375-2062", 1 );
call sp_AgregarServicios("10-3-19", "Cena", "21:00", " 1 Av. 3-21 Zona 7 Guatemala", "3948-2201", 2 );
call sp_AgregarServicios("27-8-19", "Almuerzo Empresarial", "13:00", "3 Av. 8-39 Zona 16 Guatemala", "2039-4853", 3 );
call sp_AgregarServicios("11-10-19", "Cena", "20:00", "45 Av. 2-81 Zona 8 Guatemala", "1203-9245", 4 );
call sp_AgregarServicios("12-2-20", "Desayuno", "7:30", "12 Av. 3-95 Zona 11 Guatemala", "2938-4375", 5 );

call sp_AgregarPresupuesto("15-3-19", 15000, 1);
call sp_AgregarPresupuesto("07-1-19", 10000, 2);
call sp_AgregarPresupuesto("20-5-19", 18000, 3);
call sp_AgregarPresupuesto("28-6-19", 13000, 4);
call sp_AgregarPresupuesto("15-12-19", 5000, 5);

call sp_AgregarSempleado("12-5-19", "19:00", "14 Av. 6-87 Zona 6 Guatemala", 1, 2);
call sp_AgregarSempleado("12-5-19", "19:00", "14 Av. 6-87 Zona 6 Guatemala", 1, 1);
call sp_AgregarSempleado("12-5-19", "19:00", "14 Av. 6-87 Zona 6 Guatemala", 1, 4);
call sp_AgregarSempleado("10-3-19", "21:00", " 1 Av. 3-21 Zona 7 Guatemala", 2, 3);
call sp_AgregarSempleado("10-3-19", "21:00", " 1 Av. 3-21 Zona 7 Guatemala", 2, 6);
call sp_AgregarSempleado("10-3-19", "21:00", " 1 Av. 3-21 Zona 7 Guatemala", 2, 5);
call sp_AgregarSempleado("27-8-19", "13:00", "3 Av. 8-39 Zona 16 Guatemala", 3, 1);
call sp_AgregarSempleado("27-8-19", "13:00", "3 Av. 8-39 Zona 16 Guatemala", 3, 2);
call sp_AgregarSempleado("27-8-19", "13:00", "3 Av. 8-39 Zona 16 Guatemala", 3, 6);
call sp_AgregarSempleado("11-10-19", "20:00", "45 Av. 2-81 Zona 8 Guatemala", 4, 3);
call sp_AgregarSempleado("11-10-19", "20:00", "45 Av. 2-81 Zona 8 Guatemala", 4, 6);
call sp_AgregarSempleado("11-10-19", "20:00", "45 Av. 2-81 Zona 8 Guatemala", 4, 5);
call sp_AgregarSempleado("12-2-20", "7:30", "12 Av. 3-95 Zona 11 Guatemala", 5, 4);
call sp_AgregarSempleado("12-2-20", "7:30", "12 Av. 3-95 Zona 11 Guatemala", 5, 2);
call sp_AgregarSempleado("12-2-20", "7:30", "12 Av. 3-95 Zona 11 Guatemala", 5, 6);


call sp_AgregarProductos("Huevos", 120); #1
call sp_AgregarProductos("Carne de res", 85); #2
call sp_AgregarProductos("Cerveza", 200); #3
call sp_AgregarProductos("Frijoles", 150); #4
call sp_AgregarProductos("Pasta", 90); #5
call sp_AgregarProductos("Pan", 200); #6
call sp_AgregarProductos("Jamon", 65); #7
call sp_AgregarProductos("Carne de Pollo", 70); #8
call sp_AgregarProductos("Filete de Pescado", 95); #9
call sp_AgregarProductos("Limones", 160); #10
call sp_AgregarProductos("Naranjas", 100); #11
call sp_AgregarProductos("Yogurth", 50); #12
call sp_AgregarProductos("Crema", 60); #13
call sp_AgregarProductos("Queso", 70); #14
call sp_AgregarProductos("Papas", 180); #15
call sp_AgregarProductos("Tomate", 70); #16
call sp_AgregarProductos("Cebollas", 70); #17
call sp_AgregarProductos("Fideos", 45); #18
call sp_AgregarProductos("Camaron", 60); #19
call sp_AgregarProductos("Pescado", 70); #20
call sp_AgregarProductos("Lechuga", 70); #21
call sp_AgregarProductos("Bolsas de Té", 90); #22

call sp_AgregarPlatos(150, "Huevos con Frijoles", "Es un plato de 2 huevos estrellados o revueltos con un poco de frijoles", 15, 4);
call sp_AgregarPlatos(70, "Sopa de Fideos y Camarones", "Es un boul con fideos acompañados de camarones hervidos, servidos con un té", 25, 1);
call sp_AgregarPlatos(70, "Ensalada", "Es una ensalada de lechuga, limon, tomate y cebolla que se acompaña con una naranjada", 12, 5);
call sp_AgregarPlatos(70, "Pasta en Salsa de Crema", "Es un plato de pasta bañado en una crema acompañado de papas hervidas que se sirve con una limonada", 17, 2);
call sp_AgregarPlatos(70, "Carne a la Parrilla", "Es un trozo de carne a la parrilla acompañado de cebollas a la parrilla servido con una cerveza", 30, 3);

#--------------------Actualizar--------------------#

call sp_ActualizarTempleado();
call sp_ActualizarTplato();
call sp_ActualizarEmpresas();
call sp_ActualizarEmpleados();
call sp_ActualizarServicios();
call sp_ActualizarPresupuesto();
call sp_ActualizarSempleados();
call sp_ActualizarProductos();
call sp_ActualizarPlatos();

#--------------------Eliminar--------------------#

call sp_EliminarTempleado();
call sp_EliminarTplato();
call sp_EliminarEmpresas();
call sp_EliminarEmpleados();
call sp_EliminarServicios();
call sp_EliminarPresupuesto();
call sp_EliminarSempleados();
call sp_EliminarProductos();
call sp_EliminarPlatos();

#--------------------Buscar--------------------#

call sp_BuscarTempleado();
call sp_BuscarTplato();
call sp_BuscarEmpresa();
call sp_BuscarEmpleados();
call sp_BuscarServicios();
call sp_BuscarPresupuesto();
call sp_BuscarSempleado();
call sp_BuscarProducto();
call sp_BuscarPlatos();

#--------------------Reportes--------------------#

delimiter $$
create procedure sp_SubReportePresupuesto(in var int)
begin
	select * from Empresas E inner join Presupuesto P on
		E.codigoEmpresa = P.codigoEmpresa where var = P.codigoEmpresa and E.codigoEmpresa = var group by P.cantidadPresupuesto;
end $$
delimiter ;

delimiter $$
create procedure sp_ReportePresupuesto(in var int)
begin
	select * from Empresas E inner join Servicios S on
		E.codigoEmpresa = S.codigoEmpresa where E.codigoEmpresa = var;
end $$
delimiter  ;

delimiter $$
create procedure sp_ReporteServicios(in var int)
begin
select * from Servicios S
	inner join Platos P on S.codigoServicio = P.codigoPlato
		CROSS JOIN(SELECT TP.codigoTplato, TP.descripcion from Tplato TP, platos P 
            where TP.codigoTplato = P.codigoTplato group by TP.codigoTplato )  C
				inner join productos PR on S.codigoServicio = PR.codigoProducto
					where S.codigoServicio = var and C.codigoTplato = P.codigoTplato order by S.tipoServicio;
end $$
delimiter ;

call sp_SubReportePresupuesto();
call sp_ReportePresupuesto();
call sp_ReporteServicios();
