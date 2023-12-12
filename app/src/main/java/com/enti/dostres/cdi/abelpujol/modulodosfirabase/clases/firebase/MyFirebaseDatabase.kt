package com.enti.dostres.cdi.abelpujol.modulodosfirabase.clases.firebase

import com.enti.dostres.cdi.abelpujol.modulodosfirabase.clases.firebase.models.DataBaseData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MyFirebaseDatabase {
    val dataBase = Firebase.firestore

    fun <T: DataBaseData> save(data: T, onSuccess: (T) -> Unit, onFailure: (Exception) -> Unit) {

        val table = dataBase.collection(data.GetTable())
        val id = data.id ?: table.document().id
        data.id = id

        table
            .document(id)
            .set(data)
            .addOnSuccessListener {
                onSuccess(data)
            }
            .addOnFailureListener { exception ->

                MyFirebase.crashlytics.logError(exception) {
                    key("Object", data.toString())
                    key("Error Type", "Insert Or Update Error")
                }

                onFailure(exception)
            }
    }

    inline fun <reified T:DataBaseData> find(id: String, tableName: String, crossinline onSuccess: (T) -> Unit, crossinline onFailure: (Exception) -> Unit) {
        val table = dataBase.collection(tableName)
        table
            .document(id)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                val data: T? = documentSnapshot.toObject(T::class.java)

                data?.let { data ->

                    onSuccess(data)

                } ?: kotlin.run {

                    val exception = Exception("Error On Parse Firestore DocumentSnapshot")
                    MyFirebase.crashlytics.logError(exception) {
                        key("id", id)
                        key("table", tableName)
                        key("Error Type", "Parse Error")
                        key("Snapshot", documentSnapshot.toString())
                    }

                    onFailure(exception)
                }
            }
            .addOnFailureListener { exception ->
                MyFirebase.crashlytics.logError(exception) {
                    key("id", id)
                    key("table", tableName)
                    key("Error Type", "Object Not Found")
                }

                onFailure(exception)
            }
    }

    inline fun <reified T:DataBaseData> OnTableChange(table: String, crossinline onChange: (MutableList<T>) -> Unit){

        dataBase.collection(table).addSnapshotListener{ snapShot, error ->

            //TODO CONTROL ERROR

            val objects = snapShot?.toObjects(T::class.java)

            objects?.let{ objects ->
                onChange(objects)
            }
        }
    }
}