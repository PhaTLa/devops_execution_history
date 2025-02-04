1. Install `genisoimage` to provide `mkisofs`:

    ```sh
    sudo apt-get install genisoimage
    ```

2. Find and undefine the libvirt domain (if it exists):

    ```sh
    virsh list --all
    virsh undefine demo_tf01 --remove-all-storage
    ```

3. Destroy all older resources if the previous run failed.

4. Modify `/etc/apparmor.d/libvirt/TEMPLATE.qemu` to include your pool path. This is what the `TEMPLATE.qemu` file should look like:

    ```sh
    #
    # This profile is for the domain whose UUID matches this file.
    #

    #include <tunables/global>

    profile LIBVIRT_TEMPLATE flags=(attach_disconnected) {
      #include <abstractions/libvirt-qemu>
      # Allow access to custom storage pool
      "/srv/libvirt/images/" r,
      "/srv/libvirt/images/**" rwk,
    }
    ```
