<script>
  let { open = $bindable(false), imageSrc = '' } = $props();

  function close() {
    open = false;
  }
</script>

<svelte:window onkeydown={(e) => { if (e.key === 'Escape' && open) close(); }} />

{#if open}
  <!-- svelte-ignore a11y_click_events_have_key_events -->
  <!-- svelte-ignore a11y_no_noninteractive_element_interactions -->
  <div
    class="fixed inset-0 z-50 flex items-center justify-center bg-black/90 p-4 sm:p-6 backdrop-blur-sm"
    onclick={close}
    role="dialog"
    tabindex="-1"
  >
    <button
      class="absolute top-5 right-6 text-white text-3xl leading-none hover:text-[#58a6ff] cursor-pointer transition-colors"
      onclick={close}
      aria-label="Close Preview"
    >
      &times;
    </button>
    <!-- svelte-ignore a11y_click_events_have_key_events -->
    <!-- svelte-ignore a11y_no_noninteractive_element_interactions -->
    <img
      src={imageSrc}
      alt="Preview"
      class="max-w-[95%] max-h-[90vh] rounded-md shadow-2xl object-contain"
      onclick={(e) => e.stopPropagation()}
    />
  </div>
{/if}
