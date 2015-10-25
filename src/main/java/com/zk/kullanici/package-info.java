/** 
Edit movies. An edit is an 'add', 'change', or 'delete'.

<P>The flow is as follows:
<PRE>{@link hirondelle.movies.main.MainWindow}
  -> {@link KullaniciActionEkle.movies.edit.MovieActionAdd} (simple Swing Action) - similar for Change
    -> {@link KullaniciView.movies.edit.MovieView} (dialog)
      -> {@link KullaniciController.movies.edit.MovieController}
        -> {@link Kullanici.movies.edit.Movie} (model) 
        -> {@link KullaniciDAO.movies.edit.MovieDAO} (data access)</PRE>

The delete operation does not proceed through the controller. Many would prefer to 
change that. 
*/
package com.zk.kullanici;