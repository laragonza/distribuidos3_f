package Biblioteca;

/**
 * Biblioteca/GestionBibliotecaHolder.java .
 * Generated by the IDL-to-Java compiler (portable), version "3.2"
 * from Biblioteca.idl
 * domingo 11 de mayo de 2025 11H43' CEST
 */

public final class GestionBibliotecaHolder implements org.omg.CORBA.portable.Streamable
{
  public Biblioteca.GestionBiblioteca value = null;

  public GestionBibliotecaHolder ()
  {
  }

  public GestionBibliotecaHolder (Biblioteca.GestionBiblioteca initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = Biblioteca.GestionBibliotecaHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    Biblioteca.GestionBibliotecaHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return Biblioteca.GestionBibliotecaHelper.type ();
  }

}
